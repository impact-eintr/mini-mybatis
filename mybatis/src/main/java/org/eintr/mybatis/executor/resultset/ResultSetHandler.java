package org.eintr.mybatis.executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

// 结果集处理器
public interface ResultSetHandler {
    <E> List<E> handleResultSets(Statement statement) throws SQLException;
}
