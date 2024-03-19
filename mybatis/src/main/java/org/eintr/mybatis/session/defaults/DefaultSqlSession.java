package org.eintr.mybatis.session.defaults;

import org.eintr.mybatis.binding.MapperRegistry;
import org.eintr.mybatis.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    private MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry registry) {
        this.mapperRegistry = registry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement) {
        return (T) ("一个代理的操作 "+statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("一个代理的操作 "+statement+" 入参 "+parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }
}
