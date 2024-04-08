package org.eintr.mybatis.session.defaults;

import org.eintr.mybatis.Transaction.Transaction;
import org.eintr.mybatis.Transaction.TransactionFactory;
import org.eintr.mybatis.executor.Executor;
import org.eintr.mybatis.mapping.Environment;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.SqlSession;
import org.eintr.mybatis.session.SqlSessionFactory;
import org.eintr.mybatis.session.TransactionIsolationLevel;

import java.sql.SQLException;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    @Override
    public SqlSession openSession() {
        Transaction tx = null;
        try {
            // 获取数据源
            final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(),
                    TransactionIsolationLevel.READ_COMMITTED, false);
            // 新建执行器
            final Executor executor = configuration.newExcutor(tx);
            // 用执行器构建session
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {}
            throw new RuntimeException("Error opening session. Cause: " + e);
        }
    }

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }
}
