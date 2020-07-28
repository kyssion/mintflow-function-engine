package org.mintflow.sql;

import org.mintflow.reflection.MirrorObject;
import org.mintflow.sql.basis.ConditionSqlBase;
import org.mintflow.sql.type.SqlType;

public class Update extends ConditionSqlBase {

    private Update(SqlType sqlType){
        super(sqlType);
    }

    public Update sql(){
        return new Update(SqlType.UPDATE);
    }

    public Update update(String tableName,Object item){
        MirrorObject mirrorObject = MirrorObject.forObject(item);
        String[] names = mirrorObject.getGetterNames();
        this.sql.append(UPDATE).append(SPLIT).append(TAG).append(tableName).append(TAG);
        StringBuilder setData = new StringBuilder();
        boolean isStart = true;
        for(String paramName:names){
            Object paramValue = mirrorObject.getValue(paramName);
            if(paramValue!=null){
                if(isStart){
                    setData.append(TAG).append(paramName).append(TAG).append(EQUAL).append(PLACEHOLDER);
                    isStart = false;
                }
                setData.append(COMMA).append(TAG).append(paramName).append(TAG).append(EQUAL).append(PLACEHOLDER);
                this.paramList.add(paramValue);
            }
        }
        if(setData.length()>0){
            this.sql.append(SET).append(SPLIT).append(setData).append(SPLIT);
        }
        return this;
    }

    public Update update(Object object){
        return update(getTableName(object.getClass().getName()),object);
    }

}

