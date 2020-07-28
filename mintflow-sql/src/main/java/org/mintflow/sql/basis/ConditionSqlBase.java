package org.mintflow.sql.basis;

import org.mintflow.sql.Select;
import org.mintflow.sql.Sql;
import org.mintflow.sql.type.SqlType;

import java.util.Arrays;
import java.util.List;

public class ConditionSqlBase extends SqlBase {

    public interface ChildCondition {
        StringBuilder condition(ConditionSqlBase conditionSqlBase);
    }

    protected ConditionSqlBase(SqlType sqlType) {
        super(sqlType);
        conditionSql = new StringBuilder();
    }

    public ConditionSqlBase eq(String params, Object item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(EQUAL).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase notEq(String params, Object item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_EQUAL).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase gt(String params, Object item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(GREATER_THAN).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase gte(String params, Object item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(GREATER_THAN_AND_EQUAL_TO).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase lt(String params, Object item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(LESS_THAN).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase ltq(String params, Object item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(LESS_THAN_AND_EQUAL_TO).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase like(String params, String item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(BLURRY + item + BLURRY);
        return this;
    }

    public ConditionSqlBase notLike(String params, String item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(BLURRY + item + BLURRY);
        return this;
    }

    public ConditionSqlBase likeLeft(String params, String item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(BLURRY + item);
        return this;
    }

    public ConditionSqlBase likeRight(String params, String item) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item + BLURRY);
        return this;
    }

    public ConditionSqlBase isNull(String params) {
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(IS_NULL).append(SPLIT);
        return this;
    }

    public ConditionSqlBase isNotNull(String paramName) {
        conditionSql.append(TAG).append(paramName).append(TAG).append(SPLIT).append(IS_NOT_NULL).append(SPLIT);
        return this;
    }

    public ConditionSqlBase in(String paramName, List<Object> itemList) {
        StringBuilder paramsStr = createPlaceholderArrays(itemList.size());
        conditionSql.append(TAG).append(paramName).append(TAG).append(SPLIT).append(IN).append(SPLIT)
                .append(LEFT_PARENTHESIS).append(paramsStr).append(RIGHT_PARENTHESIS).append(SPLIT);
        this.paramList.addAll(itemList);
        return this;
    }

    public ConditionSqlBase notIn(String paramName, List<Object> itemList) {
        StringBuilder paramsStr = createPlaceholderArrays(itemList.size());
        conditionSql.append(TAG).append(paramName).append(TAG).append(SPLIT).append(NOT_IN).append(SPLIT)
                .append(LEFT_PARENTHESIS).append(paramsStr).append(RIGHT_PARENTHESIS).append(SPLIT);
        this.paramList.addAll(itemList);
        return this;
    }

    public ConditionSqlBase between(String paramName, Object start, Object end) {
        betweenCondition(paramName, start, end, BETWEEN);
        return this;
    }

    public ConditionSqlBase notBetween(String paramName, Object start, Object end) {
        betweenCondition(paramName, start, end, NOT_BETWEEN);
        return this;
    }

    private void betweenCondition(String paramName, Object start, Object end, String type) {
        conditionSql.append(LEFT_PARENTHESIS).append(TAG).append(paramName).append(TAG).append(SPLIT)
                .append(type).append(SPLIT)
                .append(PLACEHOLDER).append(SPLIT)
                .append(AND).append(SPLIT)
                .append(PLACEHOLDER).append(SPLIT).append(RIGHT_PARENTHESIS).append(SPLIT);
        this.paramList.add(start);
        this.paramList.add(end);
    }

    public ConditionSqlBase or() {
        conditionSql.append(OR).append(SPLIT);
        return this;
    }

    public ConditionSqlBase and() {
        conditionSql.append(AND).append(SPLIT);
        return this;
    }

    public ConditionSqlBase or(ChildCondition childCondition) {
        conditionSql.append(OR).append(SPLIT).append(LEFT_PARENTHESIS).append(SPLIT)
                .append(childCondition.condition(this))
                .append(RIGHT_PARENTHESIS).append(SPLIT);
        return this;
    }

    public ConditionSqlBase and(ChildCondition childCondition) {
        conditionSql.append(AND).append(SPLIT).append(LEFT_PARENTHESIS).append(SPLIT)
                .append(childCondition.condition(this))
                .append(RIGHT_PARENTHESIS).append(SPLIT);
        return this;
    }

    public ConditionSqlBase groupBy(String... paramNames) {
        if (groupBySql.length() != 0) {
            return this;
        }
        StringBuilder paramsArrays = createParamsArrays((Object[]) paramNames);
        if (groupBySql.length() != 0) {
            groupBySql.append(COMMA).append(paramsArrays);
        }else{
            groupBySql.append(paramsArrays);
        }
        return this;
    }

    public ConditionSqlBase orderByDesc(String... paramNames) {
        boolean[] isAscArr = new boolean[paramNames.length];
        Arrays.fill(isAscArr, false);
        return createOrderBy(isAscArr, paramNames);
    }

    public ConditionSqlBase orderByAsc(String... paramNames) {
        boolean[] isAscArr = new boolean[paramNames.length];
        Arrays.fill(isAscArr, true);
        return createOrderBy(isAscArr, paramNames);
    }


    public ConditionSqlBase createOrderBy(boolean[] isAsc, String[] paramNames) {
        if (this.sqlType != SqlType.SELECT) {
            return this;
        }
        if (isAsc.length != paramNames.length) {
            return this;
        }
        for (int a = 0; a < paramNames.length; a++) {
            if (a != 0 || orderBySql.length() != 0) {
                orderBySql.append(",");
            }
            if (isAsc[a]) {
                orderBySql.append(TAG).append(paramNames[a]).append(TAG).append(SPLIT).append(ASC);
            } else {
                orderBySql.append(TAG).append(paramNames[a]).append(TAG).append(SPLIT).append(DESC);
            }
        }
        return this;
    }

    public ConditionSqlBase limit(int num) {
        if (this.sqlType != SqlType.SELECT) {
            return this;
        }
        if (limitSql.length() != 0) {
            limitSql = new StringBuilder();
        }
        limitSql.append(LIMIT).append(SPLIT).append(PLACEHOLDER).append(SPLIT);
        paramList.add(num);
        return this;
    }

    public ConditionSqlBase limit(int start, int num) {
        if (this.sqlType != SqlType.SELECT) {
            return this;
        }
        if (limitSql.length() != 0) {
            limitSql = new StringBuilder();
        }
        limitSql.append(LIMIT).append(SPLIT).append(PLACEHOLDER).append(COMMA).append(PLACEHOLDER).append(SPLIT);
        paramList.add(start);
        paramList.add(num);
        return this;
    }

    /**
     * use subqueries
     */


    public ConditionSqlBase in(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, IN);
        return this;
    }

    public ConditionSqlBase notIn(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, NOT_IN);
        return this;
    }

    public ConditionSqlBase ltq(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, LESS_THAN_AND_EQUAL_TO);
        return this;
    }

    public ConditionSqlBase eq(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, EQUAL);
        return this;
    }

    public ConditionSqlBase notEq(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, NOT_EQUAL);
        return this;
    }

    public ConditionSqlBase gte(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, GREATER_THAN_AND_EQUAL_TO);
        return this;
    }

    public ConditionSqlBase gt(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, GREATER_THAN);
        return this;
    }

    public ConditionSqlBase lt(String params, Select childSqlBase) {
        useSubqueries(params, childSqlBase, LESS_THAN);
        return this;
    }

    private void useSubqueries(String params, Select childSqlBase, String type) {
        Sql childSql = childSqlBase.build();
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(type).append(SPLIT).
                append(LEFT_PARENTHESIS).append(childSql.getSql()).append(RIGHT_PARENTHESIS).append(SPLIT);
        this.paramList.addAll(childSql.getParamsList());
    }
}
