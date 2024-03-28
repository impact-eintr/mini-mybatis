package org.eintr.mybatis.session.defaults;

import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.SqlSession;
import org.eintr.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }
}
