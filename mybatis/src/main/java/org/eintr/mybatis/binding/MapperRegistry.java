package org.eintr.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapperRegistry {
    // 将已经添加的Mapper添加缓存
    private final Map<Class<?>, MapperProxyFactory<?>> knowMappers = new HashMap<>();

    private Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knowMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance.Case: " + e, e);
        }
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                // 如果重复添加了，报错
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            knowMappers.put(type, new MapperProxyFactory<>(type)); // 注册代理工厂
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knowMappers.containsKey(type);
    }

    public void addMappers(String packageName) {
        Set<Class<?>> mappers = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mappers) {
            addMapper(mapperClass);
        }
    }
}
