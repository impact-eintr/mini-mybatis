package org.eintr.mybatis.reflection;

import org.eintr.mybatis.reflection.factory.ObjectFactory;
import org.eintr.mybatis.reflection.property.PropertyTokenizer;
import org.eintr.mybatis.reflection.wrapper.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MetaObject {
    // 原对象
    private Object originalObject;
    //
    /*对象包装器 实际承担了对象信息的处理
    ***** objectWrapper 重要!!! ******
    * */
    private ObjectWrapper objectWrapper;
    // 对象工厂
    private ObjectFactory objectFactory;
    // 对象包装工厂
    private ObjectWrapperFactory objectWrapperFactory;

    private MetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory) {
        this.originalObject = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;

        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (object instanceof Map) {
            this.objectWrapper = new MapWrapper(this, (Map) object);
        } else if (object instanceof Collection) {
            this.objectWrapper = new CollectionWrapper(this, (Collection) object);
        } else {
            this.objectWrapper = new BeanWrapper(this, object);
        }
    }

    public static MetaObject forObject(Object object, ObjectFactory objectFactory,
                                       ObjectWrapperFactory objectWrapperFactory) {
        if (object == null) {
            return SystemMetaObject.NULL_META_OBJECT;
        } else {
            return new MetaObject(object, objectFactory, objectWrapperFactory);
        }
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    // 查找属性
    public String findProperty(String propName, boolean useCamelCaseMapping) {
        return objectWrapper.findProperty(propName, useCamelCaseMapping);
    }

    // 取得getter的名字列表
    public String[] getGetterNames() {
        return objectWrapper.getGetterNames();
    }

    // 取得setter的名字列表
    public String[] getSetterNames() {
        return objectWrapper.getSetterNames();
    }

    // 取得setter的类型列表
    public Class<?> getSetterType(String name) {
        return objectWrapper.getSetterType(name);
    }

    // 取得getter的类型列表
    public Class<?> getGetterType(String name) {
        return objectWrapper.getGetterType(name);
    }

    //是否有指定的setter
    public boolean hasSetter(String name) {
        return objectWrapper.hasSetter(name);
    }

    // 是否有指定的getter
    public boolean hasGetter(String name) {
        return objectWrapper.hasGetter(name);
    }

    // 取得值
    public Object getValue(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) { // 为子属性构建元对象
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName()); // 使用当前属性 构造元对象
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                return null;
            } else {
                return metaValue.getValue(prop.getChildren());
            }
        } else {
            // 到最后一层 进行委派
            return objectWrapper.get(prop);
        }
    }

    // 设置值
    public void setValue(String name, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) { // 到达最后一层
                if (value == null && prop.getChildren() != null) {
                    return;
                } else {
                    metaValue = objectWrapper.instantiatePropertyValue(name, prop, objectFactory);
                }
            }
            // 递归调用setValue
            metaValue.setValue(prop.getChildren(), value);
        } else {
            // 到最后一层 进行委派
            objectWrapper.set(prop, value);
        }
    }

    // 为一个属性生成元对象
    public MetaObject metaObjectForProperty(String name) {
        Object value = getValue(name); //递归调用
        return MetaObject.forObject(value, objectFactory, objectWrapperFactory);
    }

    // 是否是集合
    public boolean isCollection() {
        return objectWrapper.isCollection();
    }

    // 添加属性
    public void add(Object object) {
        objectWrapper.add(object);
    }

    public <E> void addAll(List<E> list) {
        objectWrapper.addAll(list);
    }

}
