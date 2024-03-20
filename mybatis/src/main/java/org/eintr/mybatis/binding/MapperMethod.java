package org.eintr.mybatis.binding;

import org.eintr.mybatis.mapping.MappedStatement;
import org.eintr.mybatis.mapping.SqlCommandType;
import org.eintr.mybatis.session.Configuration;
import org.eintr.mybatis.session.SqlSession;

import java.lang.reflect.Method;

public class MapperMethod {

    private final SqlCommand command;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) throws Exception {
        this.command = new SqlCommand(configuration, mapperInterface, method);
    }

    // 具体执行
    public Object execute(SqlSession sqlSession, Object[] args) {
        Object res = null;
        switch (command.getType()) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                res = sqlSession.selectOne(command.getName(), args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return res;
    }

    public static class SqlCommand {
        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration,
                          Class<?> mapperInterface, Method method) throws Exception{
            String stateName = mapperInterface.getName() + "." + method.getName();
            name = stateName;
            MappedStatement ms = configuration.getMappedStatement(stateName);
            if (ms == null) {
                throw new Exception("no sush MethodMapper");
            }
            type = ms.getSqlCommandType();
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }
}
