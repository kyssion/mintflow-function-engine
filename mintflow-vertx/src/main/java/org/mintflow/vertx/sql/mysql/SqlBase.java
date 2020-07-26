package org.mintflow.vertx.sql.mysql;

import java.util.Arrays;
import java.util.List;

public class SqlBase extends SqlSymbol{
    protected StringBuilder sql;
    protected List<Object> paramList;
    protected SqlType sqlType;
    protected SqlBase(SqlType sqlType){
        this.sqlType = sqlType;
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
            arrays.append(COMMA).append(str);
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
        return new Sql(this.sql.toString(),this.paramList);
    }
}
