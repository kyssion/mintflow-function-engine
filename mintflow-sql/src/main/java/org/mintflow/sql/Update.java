package org.mintflow.sql;

import org.mintflow.reflection.SampleMirrorObject;
import org.mintflow.sql.basis.ConditionSqlBase;
import org.mintflow.sql.type.SqlType;

import java.util.List;

public class Update extends ConditionSqlBase {

    private Update(SqlType sqlType) {
        super(sqlType);
    }

    public static Update sql() {
        return new Update(SqlType.UPDATE);
    }

    public Update update(Object item) {
        String tableName = getTableName(item);
        SampleMirrorObject sampleMirrorObject = SampleMirrorObject.forObject(item);
        List<String> names = findParamsList(item);
        this.sql.append(UPDATE).append(SPLIT).append(TAG).append(tableName).append(TAG).append(SPLIT);
        StringBuilder setData = new StringBuilder();
        boolean isStart = true;
        for (String paramName : names) {
            Object paramValue = sampleMirrorObject.getValue(paramName);
            if (paramValue != null) {
                if (isStart) {
                    setData.append(TAG).append(paramName).append(TAG).append(EQUAL).append(PLACEHOLDER);
                    isStart = false;
                }else{
                    setData.append(COMMA).append(TAG).append(paramName).append(TAG).append(EQUAL).append(PLACEHOLDER);
                }
                this.paramList.add(paramValue);
            }
        }
        if (setData.length() > 0) {
            this.sql.append(SET).append(SPLIT).append(setData).append(SPLIT);
        }else{
            throw  new RuntimeException();
        }
        return this;
    }

}

