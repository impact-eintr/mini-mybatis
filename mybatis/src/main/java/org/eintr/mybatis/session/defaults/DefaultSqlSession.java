package org.eintr.mybatis.session.defaults;

import org.eintr.mybatis.binding.MapperRegistry;
import org.eintr.mybatis.executor.Executor;
import org.eintr.mybatis.mapping.BoundSql;
import org.eintr.mybatis.mapping.Environment;
import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.SqlSession;
import org.omg.CORBA.ObjectHelper;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement) {
        return this.selectOne(statement, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement, Object parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            List<T> list = executor.query(mappedStatement, parameter, Executor.NO_RESULT_HANDLER,
                    mappedStatement.getBoundSql());
            return list.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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
