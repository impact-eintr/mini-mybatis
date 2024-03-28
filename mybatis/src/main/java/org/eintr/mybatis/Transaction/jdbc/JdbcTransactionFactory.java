package org.eintr.mybatis.Transaction.jdbc;

import org.eintr.mybatis.Transaction.Transaction;
import org.eintr.mybatis.Transaction.TransactionFactory;
import org.eintr.mybatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

public class JdbcTransactionFactory implements TransactionFactory  {


    @Override
    public Transaction newTransaction(Connection connection) {
        return new JdbcTransaction(connection);
    }

    @Override
    public Transaction newTranscation(DataSource dataSource, TransactionIsolationLevel level,
                                      boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }
}
