package org.eintr.mybatis.binding;

import org.eintr.mybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;


// 这个代理的作用就是将一个未知类的未知函数映射到一个未知Sql中 通过配置文件的方式
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionID = -1145141229L;

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    // 代理的三要素: 数据库连接  配置文件解析出来的类 类函数与Sql的映射关系
    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface,
                       Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) { // 如果调用 toString equals hashCode 等父类方法
            return method.invoke(this, args);
        } else { // 其他方法都将被 代理类 代理
            final MapperMethod mappedMethod = cachedMapperMethod(method);
            return mappedMethod.execute(sqlSession, args);
        }
    }

    // 获取缓存中的 MapperMethod
    private MapperMethod cachedMapperMethod(Method method) throws Exception {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) { // 在所有映射关系中寻找
            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
}
