package org.mintflow.sql;

import org.mintflow.sql.basis.ConditionSqlBase;
import org.mintflow.sql.type.SqlType;

public class Delete extends ConditionSqlBase {

    protected Delete(SqlType sqlType) {
        super(sqlType);
    }

    public Delete sql(){
        return new Delete(SqlType.DELETE);
    }

    public Delete delete(String tableName){
        this.sql.append(DELETE).append(SPLIT).append(FROM).append(TAG).append(tableName).append(TAG).append(SPLIT);
        return this;
    }
}
