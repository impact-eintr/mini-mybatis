package org.eintr.mybatis.executor.statement;

import org.eintr.mybatis.executor.Executor;
import org.eintr.mybatis.executor.resultset.ResultSetHandler;
import org.eintr.mybatis.mapping.BoundSql;
import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

// 基础声明处理器
public abstract class BaseStatementHandler implements StatementHandler {
    protected final Configuration configuration; // 配置
    protected final Executor executor; // sql执行器
    protected final MappedStatement mappedStatement; // 映射后的sql
    protected final Object parameterObject; // sql参数
    protected final ResultSetHandler resultSetHandler; // 结果集处理器

    protected BoundSql boundSql; // 原始sql

    public BaseStatementHandler(Executor executor, MappedStatement mappedStatement,
                                Object parameterObject, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.boundSql = boundSql;
        this.parameterObject = parameterObject;
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, boundSql);
    }

    // 预处理
    @Override
    public Statement prepaer(Connection connection) throws SQLException {
        Statement statement = null;
        try {
            statement = instantiateStatement(connection);
            statement.setQueryTimeout(350);
            statement.setFetchSize(10000);
            return statement;
        } catch (Exception e) {
            throw new RuntimeException("Error preparing statement. Cause: " + e, e);
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;
}
