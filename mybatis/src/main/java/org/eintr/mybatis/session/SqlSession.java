package org.eintr.mybatis.session;

public interface SqlSession {
    // 根据 statement 获取一条数据库记录
    <T> T selectOne(String statement);

    // 根据 statement以及传参 获取一条数据库记录
    <T> T selectOne(String statement, Object parameter);

    // 获取映射器
    <T> T getMapper(Class<T> type);

    Configuration getConfiguration();
}
