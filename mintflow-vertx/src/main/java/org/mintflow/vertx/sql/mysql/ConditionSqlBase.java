package org.mintflow.vertx.sql.mysql;

public class ConditionSqlBase extends SqlBase {

    private StringBuilder conditionSql;

    protected ConditionSqlBase(SqlType sqlType) {
        super(sqlType);
        conditionSql = new StringBuilder();
    }

    public ConditionSqlBase eq(String params, Object item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(EQUAL).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase gt(String params, Object item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(GREATER_THAN).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase gte(String params, Object item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(GREATER_THAN_AND_EQUAL_TO).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase lt(String params, Object item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(LESS_THAN).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase ltq(String params, Object item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(LESS_THAN_AND_EQUAL_TO).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item);
        return this;
    }

    public ConditionSqlBase like(String params, String item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(BLURRY+item+BLURRY);
        return this;
    }

    public ConditionSqlBase notLike(String params, String item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(BLURRY+item+BLURRY);
        return this;
    }

    public ConditionSqlBase likeLeft(String params, String item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(BLURRY+item);
        return this;
    }

    public ConditionSqlBase likeRight(String params, String item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_LIKE).append(SPLIT).
                append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(item+BLURRY);
        return this;
    }

    public ConditionSqlBase isNull(String name){
        conditionSql.append(TAG).append(name).append(TAG).append(SPLIT).append(IS_NULL).append(SPLIT);
        return this;
    }

    public ConditionSqlBase isNotNull(String name){
        conditionSql.append(TAG).append(name).append(TAG).append(SPLIT).append(IS_NOT_NULL).append(SPLIT);
        return this;
    }



    public ConditionSqlBase ltq(String params, Select childSqlBase){
        useSubqueries(params,childSqlBase,LESS_THAN_AND_EQUAL_TO);
        return this;
    }

    public ConditionSqlBase eq(String params, Select childSqlBase){
        useSubqueries(params,childSqlBase,EQUAL);
        return this;
    }

    public ConditionSqlBase gte(String params, Select childSqlBase){
        useSubqueries(params,childSqlBase,GREATER_THAN_AND_EQUAL_TO);
        return this;
    }

    public ConditionSqlBase gt(String params , Select childSqlBase){
        useSubqueries(params,childSqlBase,GREATER_THAN);
        return this;
    }

    public ConditionSqlBase lt(String params, Select childSqlBase){
        useSubqueries(params,childSqlBase,LESS_THAN);
        return this;
    }

    private void useSubqueries(String params,Select childSqlBase,String type){
        Sql childSql = childSqlBase.build();
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(type).append(SPLIT).
                append(LEFT_PARENTHESIS).append(childSql.getSql()).append(RIGHT_PARENTHESIS).append(SPLIT);
        this.paramList.addAll(childSql.getParamsList());
    }
}
