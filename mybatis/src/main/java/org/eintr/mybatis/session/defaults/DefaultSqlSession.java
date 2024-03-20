package org.eintr.mybatis.session.defaults;

import org.eintr.mybatis.binding.MapperRegistry;
import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement) {
        return (T) ("一个代理的操作 "+statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return (T) ("一个代理的操作 "+statement+" 入参 "+parameter+"\n执行的SQL: "+mappedStatement.getSql());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
