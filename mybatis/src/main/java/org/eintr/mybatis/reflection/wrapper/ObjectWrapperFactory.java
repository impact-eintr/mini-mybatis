package org.eintr.mybatis.reflection.wrapper;

import org.eintr.mybatis.reflection.MetaObject;

// 对象包装工厂
public interface ObjectWrapperFactory {
    // 判断object有没有包装器
    boolean hasWrapperFor(Object object);
    // 得到包装器
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
