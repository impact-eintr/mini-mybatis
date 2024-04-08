package org.eintr;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eintr.Dao.IUserDao;
import org.eintr.mybatis.datasource.pooled.PooledDataSource;
import org.eintr.mybatis.io.Resources;
import org.eintr.mybatis.session.SqlSession;
import org.eintr.mybatis.session.SqlSessionFactory;
import org.eintr.mybatis.session.SqlSessionFactoryBuilder;
import org.eintr.po.User;


import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }


    public void test_proxy_class() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 3. 测试验证
        User user = userDao.queryUserInfoById(1L);
    }

    public void test_pooled() throws SQLException, InterruptedException {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setDriver("com.mysql.cj.jdbc.Driver");
        pooledDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true");
        pooledDataSource.setUsername("root");
        pooledDataSource.setPassword("yxwdmysql");
        // 持续获得链接
        while (true) {
            Connection connection1 = pooledDataSource.getConnection();
            Connection connection2 = pooledDataSource.getConnection();
            Connection connection3 = pooledDataSource.getConnection();
            Thread.sleep(1000);
            // 注释掉/不注释掉测试
            connection1.close();
            connection2.close();
            connection3.close();
        }
    }
}
