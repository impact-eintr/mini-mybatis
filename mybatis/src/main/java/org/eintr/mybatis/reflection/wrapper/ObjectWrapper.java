package org.eintr.mybatis.reflection.wrapper;

import org.eintr.mybatis.reflection.MetaObject;
import org.eintr.mybatis.reflection.factory.ObjectFactory;
import org.eintr.mybatis.reflection.property.PropertyTokenizer;

import java.util.List;

// 对象包装器
public interface ObjectWrapper {
    Object get(PropertyTokenizer prop);

    void set(PropertyTokenizer prop, Object value);
    // 查找属性
    String findProperty(String name, boolean useCamelCaseMapping);

    // 取得 getter 的名字列表
    String[] getGetterNames();
    // 取得 setter 的名字列表
    String[] getSetterNames();
    // 取得setter的类型
    Class<?> getSetterType(String name);
    // 取得getter的类型
    Class<?> getGetterType(String name);
    // 是否有指定的setter
    boolean hasSetter(String name);
    // 是否有指定的getter
    boolean hasGetter(String name);

    // 实例化属性
    MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

    // 是否是集合
    boolean isCollection();
    // 添加属性
    void add(Object element);
    // 添加全部属性
    <E> void addAll(List<E> element);
}
