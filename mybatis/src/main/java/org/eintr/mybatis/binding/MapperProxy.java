package org.eintr.mybatis.binding;

import org.eintr.mybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionID = -1145141229L;

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) { // 如果调用 toString equals hashCode 等父类方法
            return method.invoke(this, args);
        } else { // 其他方法都将被 代理类 代理
            return sqlSession.selectOne(method.getName(), args);
        }
    }
}
