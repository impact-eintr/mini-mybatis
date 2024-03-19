package org.eintr.mybatis.session;

public interface SqlSessionFactory {
    // 打开一个sql session
    SqlSession openSession();
}
