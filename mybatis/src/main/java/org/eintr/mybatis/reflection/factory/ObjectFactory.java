package org.eintr.mybatis.reflection.factory;

import java.util.List;
import java.util.Properties;

// 对象工厂方法
public interface ObjectFactory {
    // 给对象设置属性
    void setProperties(Properties properties);
    // 构造对象
    <T> T create(Class<T> type);
    // 使用指定的构造函数和构造函数参数
    <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);
    <T> boolean isCollection(Class<T> type);
}
