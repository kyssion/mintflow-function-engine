package org.mintflow.sql.basis;

import org.mintflow.sql.Sql;
import org.mintflow.sql.type.SqlType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlBase extends SqlSymbol{
    protected StringBuilder sql;
    protected StringBuilder conditionSql;
    protected StringBuilder groupBySql;
    protected StringBuilder orderBySql;
    protected StringBuilder limitSql;

    protected List<Object> paramList;
    protected SqlType sqlType;
    protected SqlBase(SqlType sqlType){
        this.sqlType = sqlType;
        this.paramList = new ArrayList<>();
        this.sql = new StringBuilder();
        this.conditionSql = new StringBuilder();
        this.groupBySql = new StringBuilder();
        this.orderBySql = new StringBuilder();
        this.limitSql = new StringBuilder();
    }

    protected StringBuilder createParamsArrays(List<Object> params){
        StringBuilder arrays = new StringBuilder();
        if(params==null||params.size()==0){
            return arrays;
        }

        boolean isStart=true;
        for(Object str : params){
            if(isStart){
                arrays.append(TAG).append(str).append(TAG);
                isStart = false;
                continue;
            }
            arrays.append(COMMA).append(TAG).append(str).append(TAG);
        }
        return arrays;
    }

    protected StringBuilder createParamsArrays(Object...params){
        return createParamsArrays(Arrays.asList(params));
    }

    protected StringBuilder createPlaceholderArrays(int length){
        StringBuilder arrays = new StringBuilder();
        boolean isStart = true;
        while(length>=0){
            if(isStart){
                isStart = false;
                arrays.append(PLACEHOLDER);
                continue;
            }
            arrays.append(COMMA).append(PLACEHOLDER);
            length--;
        }
        return arrays;
    }

    protected String underlineToCamel(String param) {
        if (param==null) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(param.charAt(i));
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public Sql build(){

        if(this.conditionSql.length()>0){
            this.sql.append(SPLIT).append(WHERE).append(SPLIT).append(this.conditionSql);
        }

        if(this.groupBySql.length()>0){
            this.sql.append(SPLIT).append(this.groupBySql);
        }

        if(this.orderBySql.length()>0){
            this.sql.append(SPLIT).append(this.groupBySql);
        }

        return new Sql(this.sql.toString(),this.paramList);
    }

    public String getTableName(String name){
        String tableName = underlineToCamel(name);
        if(tableName.endsWith(BEAN_END_WITH)){
            tableName = tableName.substring(0,tableName.length()-3);
        }
        return tableName;
    }
}
