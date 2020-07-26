package org.mintflow.vertx.sql.mysql;

import java.util.List;

public class SqlBase {
    protected StringBuilder sql;
    protected List<Object> paramList;
    protected static final String SPLIT = " ";
    protected static final String TAG = "`";
    protected static final char UNDERLINE = '_';

    protected StringBuilder createParamsArrays(String...params){
        StringBuilder arrays = new StringBuilder();
        if(params==null||params.length==0){
            return arrays;
        }

        boolean isStart=true;
        for(String str : params){
            if(isStart){
                arrays.append(TAG).append(str).append(TAG);
                isStart = false;
                continue;
            }
            arrays.append(",").append(str);
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
