package org.eintr.mybatis.executor;

import org.eintr.mybatis.Transaction.Transaction;
import org.eintr.mybatis.executor.statement.StatementHandler;
import org.eintr.mybatis.mapping.BoundSql;
import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleExecutor extends BaseExecutor {
    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }
    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object paramter,
                                  ResultHandler resultHandler, BoundSql boundSql) {
        try {
            Configuration configuration1 = ms.getConfiguration();
            StatementHandler handler = configuration1.newStatementHandler(this, ms, paramter, resultHandler, boundSql);

            Connection connection = transaction.getConnection();
            Statement statement = handler.prepaer(connection);
            handler.parameterize(statement);
            return handler.query(statement, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
