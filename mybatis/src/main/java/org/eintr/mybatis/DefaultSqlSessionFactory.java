package org.eintr.mybatis;

import com.sun.javafx.tk.ScreenConfigurationAccessor;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    // 数据库配置
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration.connection, configuration.mapperElement);
    }
}
