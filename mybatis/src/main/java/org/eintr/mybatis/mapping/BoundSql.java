package org.eintr.mybatis.mapping;

import java.util.Map;

public class BoundSql {

    private String parameterType;
    private String resultType;
    private String sql;

    private Map<Integer, String> parameterMappings;

    public BoundSql(String sql, Map<Integer, String> parameter, String parameterType, String resultType) {
        this.sql = sql;
        this.parameterMappings = parameter;
        this.parameterType = parameterType;
        this.resultType = resultType;
    }


    public String getParameterType() {
        return parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public String getSql() {
        return sql;
    }

    public Map<Integer, String> getParameterMappings() {
        return parameterMappings;
    }
}
