package org.mintflow.vertx.sql.mysql;

import org.mintflow.reflection.MirrorObject;

import java.util.ArrayList;
import java.util.List;

public class Select extends SqlBase{

    private static final String SELECT ="select";
    private static final char UNDERLINE = '_';

    private StringBuilder sql;
    private List<Object> paramList;


    public Select(){
        this.sql = new StringBuilder();
        this.paramList = new ArrayList<>();
    }

    public Select selectFrom(String tableName,String...params){
        StringBuilder str = new StringBuilder();
        str.append(SELECT).append(SPLIT).
                append(createParamsArrays(params)).append(SPLIT).
                append(TAG).append(tableName).append(TAG);
        this.sql.append(str);
        return this;
    }

    public Select selectFromWhere(Object templateData){
        MirrorObject mirrorObject = MirrorObject.forObject(templateData);
        String[] names = mirrorObject.getGetterNames();
        for(int a=0;a<names.length;a++){
            names[a] = underlineToCamel(names[a]);
        }
        String tableName = underlineToCamel(templateData.getClass().getName());
        return selectFrom(tableName,names);
    }

    private String underlineToCamel(String param) {
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

    private StringBuilder createParamsArrays(String...params){
        StringBuilder arrays = new StringBuilder();
        if(params==null||params.length==0){
            arrays.append("*");
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
}
