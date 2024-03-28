package org.eintr.mybatis.Transaction;

import org.eintr.mybatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

public interface TransactionFactory {
    Transaction newTransaction(Connection connection);

    Transaction newTranscation(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);
}
