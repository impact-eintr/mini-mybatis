package org.eintr;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eintr.mybatis.Resources;
import org.eintr.mybatis.SqlSession;
import org.eintr.mybatis.SqlSessionFactory;
import org.eintr.mybatis.SqlSessionFactoryBuilder;

import java.io.Reader;

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

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlMapper.openSession();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
