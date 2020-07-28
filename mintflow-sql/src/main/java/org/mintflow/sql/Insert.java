package org.mintflow.sql;

import org.mintflow.reflection.MirrorObject;
import org.mintflow.sql.basis.SqlBase;
import org.mintflow.sql.type.SqlType;

import java.util.List;

public class Insert extends SqlBase {

    private Insert(SqlType sqlType) {
        super(sqlType);
    }

    public static Insert sql() {
        return new Insert(SqlType.INSERT);
    }

    public <T> Insert insert(String tableName, List<T> paramsList) {
        this.sql.append(INSERT).append(SPLIT).append(INTO).append(SPLIT).append(TAG).append(tableName).append(TAG).append(SPLIT);
        MirrorObject.forObject(tableName);
        return this;
    }

    public <T> Insert insert(String tableName, T params) {

        return this;
    }

    public <T> Insert insert(T params) {

        return this;
    }

}
