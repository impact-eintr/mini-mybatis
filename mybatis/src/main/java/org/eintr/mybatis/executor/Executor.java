package org.eintr.mybatis.executor;

import org.eintr.mybatis.Transaction.Transaction;
import org.eintr.mybatis.mapping.BoundSql;
import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.session.ResultHandler;

import java.sql.SQLException;
import java.util.List;

public interface Executor { // 执行器
    ResultHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(MappedStatement ms,Object paramter, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransation();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);
}
