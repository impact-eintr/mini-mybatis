package org.eintr.mybatis.executor;

import org.eintr.mybatis.Transaction.Transaction;
import org.eintr.mybatis.mapping.BoundSql;
import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.ResultHandler;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseExecutor implements Executor {

    protected Configuration configuration;
    protected Transaction transaction;
    protected Executor wrapper;

    private boolean closed;

    public BaseExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
        this.wrapper = this;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object paramter,
                             ResultHandler resultHandler, BoundSql boundSql) {
        if (closed) {
            throw new RuntimeException("Executor was closed");
        }
        return doQuery(ms, paramter, resultHandler, boundSql);
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Object paramter,
                                           ResultHandler resultHandler, BoundSql boundSql);

    @Override
    public Transaction getTransation() {
        if (closed) {
            throw new RuntimeException("Executor was closed");
        }
        return transaction;
    }

    @Override
    public void commit(boolean required) throws SQLException {
        if (closed) {
            throw new RuntimeException("Cannot commit, transaction is already closed");
        }
        if (required) {
            transaction.commit();
        }
    }

    @Override
    public void rollback(boolean required) throws SQLException {
        if (!closed) {
            if (required) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void close(boolean forceRollback) {
        try {
            try { // 尝试回滚
                rollback(forceRollback);
            } finally { // 关闭事务
                transaction.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            transaction = null;
            closed = true;
        }
    }
}
