package org.eintr.mybatis.session.defaults;

import org.eintr.mybatis.binding.MapperRegistry;
import org.eintr.mybatis.session.SqlSession;
import org.eintr.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final MapperRegistry mapperRegistry;

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }
}
