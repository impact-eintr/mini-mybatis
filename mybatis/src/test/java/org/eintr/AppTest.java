package org.eintr;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eintr.Dao.IUserDao;
import org.eintr.mybatis.binding.MapperProxyFactory;
import org.eintr.mybatis.binding.MapperRegistry;
import org.eintr.mybatis.io.Resources;
import org.eintr.mybatis.session.SqlSession;
import org.eintr.mybatis.session.SqlSessionFactory;
import org.eintr.mybatis.session.SqlSessionFactoryBuilder;
import org.eintr.mybatis.session.defaults.DefaultSqlSession;
import org.eintr.mybatis.session.defaults.DefaultSqlSessionFactory;


import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

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
        String res = userDao.queryUserInfoById(1L);
        System.out.println(res);
    }
}
