package org.eintr.mybatis.reflection;

import org.eintr.mybatis.reflection.factory.DefaultObjectFactory;
import org.eintr.mybatis.reflection.factory.ObjectFactory;
import org.eintr.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.eintr.mybatis.reflection.wrapper.ObjectWrapperFactory;


public class SystemMetaObject {

    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

    private SystemMetaObject() {}

    private static class NullObject{}

    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
    }
}
