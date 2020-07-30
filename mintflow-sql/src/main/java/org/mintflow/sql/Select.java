package org.mintflow.sql;

import org.mintflow.reflection.MirrorObject;
import org.mintflow.reflection.SampleMirrorObject;
import org.mintflow.sql.basis.ConditionSqlBase;
import org.mintflow.sql.type.SqlType;

import java.util.List;

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
        List<String> names = findParamsList(templateData);
        for (int a = 0; a < names.size(); a++) {
            names.set(a,underlineToCamel(names.get(a)));
        }
        String tableName = getTableName(templateData);
        String[] params = new String[names.size()];
        for(int a=0;a< params.length;a++){
            params[a] = names.get(a);
        }
        return selectFrom(tableName, params);
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
