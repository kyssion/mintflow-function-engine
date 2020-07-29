package org.mintflow.sql;

import org.mintflow.reflection.MirrorObject;
import org.mintflow.sql.basis.ConditionSqlBase;
import org.mintflow.sql.type.SqlType;

public class Select extends ConditionSqlBase {

    private Select(SqlType sqlType) {
        super(sqlType);
    }

    public static Select sql() {
        return new Select(SqlType.SELECT);
    }

    public Select selectFrom(String tableName, String... params) {
        this.sql.append(SELECT).append(SPLIT)
                .append(createParamsArrays(params)).append(SPLIT)
                .append(FROM).append(SPLIT)
                .append(TAG).append(tableName).append(TAG).append(SPLIT);
        return this;
    }

    public Select selectFrom(Object templateData) {
        MirrorObject mirrorObject = MirrorObject.forObject(templateData);
        String[] names = mirrorObject.getFiledMirrorObject().getGetterNames();
        for (int a = 0; a < names.length; a++) {
            names[a] = underlineToCamel(names[a]);
        }
        String tableName = getTableName(templateData);
        return selectFrom(tableName, names);
    }

    protected StringBuilder createParamsArrays(String... params) {
        if (params.length == 1) {
            if (ALL.equals(params[0])) {
                return new StringBuilder(ALL);
            }
        }
        return super.createParamsArrays((Object[]) params);
    }

}
