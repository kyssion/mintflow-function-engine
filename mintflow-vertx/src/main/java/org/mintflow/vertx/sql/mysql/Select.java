package org.mintflow.vertx.sql.mysql;

import org.mintflow.reflection.MirrorObject;

import java.util.ArrayList;

public class Select extends ConditionSqlBase {

    private static final String SELECT ="select";

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

    public Select selectFrom(Object templateData){
        MirrorObject mirrorObject = MirrorObject.forObject(templateData);
        String[] names = mirrorObject.getGetterNames();
        for(int a=0;a<names.length;a++){
            names[a] = underlineToCamel(names[a]);
        }
        String tableName = underlineToCamel(templateData.getClass().getName());
        return selectFrom(tableName,names);
    }

    protected StringBuilder createParamsArrays(String...params){
        if(params.length==1){
            if("*".equals(params[0])){
                return new StringBuilder("*");
            }
        }
        return super.createParamsArrays(params);
    }

}
