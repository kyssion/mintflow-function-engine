package org.mintflow.sql;

import java.util.List;

public class Sql {

    private String sql;

    private List<Object> paramsList;

    public Sql(String sql,List<Object> paramsList){
        this.sql = sql;
        this.paramsList = paramsList;
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getParamsList() {
        return paramsList;
    }

}
