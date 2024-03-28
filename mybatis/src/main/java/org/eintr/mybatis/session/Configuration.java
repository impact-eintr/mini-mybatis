package org.eintr.mybatis.session;

import org.eintr.mybatis.Transaction.jdbc.JdbcTransactionFactory;
import org.eintr.mybatis.binding.MapperRegistry;
import org.eintr.mybatis.datasource.druid.DruidDataSourceFactory;
import org.eintr.mybatis.datasource.pooled.PooledDataSourceFactory;
import org.eintr.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.eintr.mybatis.mapping.Environment;
import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    // 环境
    protected Environment environment;

    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    // 在mapper文件中映射过的 Class.Method<->Sql
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();


    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);

        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
    }

    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }
}
