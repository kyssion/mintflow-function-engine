package org.mintflow.vertx.sql.mysql;

import java.util.List;

public class ConditionSqlBase extends SqlBase {

    private final StringBuilder conditionSql;

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

    public ConditionSqlBase notEq(String params,Object item){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(NOT_EQUAL).append(SPLIT).
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

    public ConditionSqlBase isNull(String params){
        conditionSql.append(TAG).append(params).append(TAG).append(SPLIT).append(IS_NULL).append(SPLIT);
        return this;
    }

    public ConditionSqlBase isNotNull(String paramName){
        conditionSql.append(TAG).append(paramName).append(TAG).append(SPLIT).append(IS_NOT_NULL).append(SPLIT);
        return this;
    }

    public ConditionSqlBase in(String paramName, List<Object> itemList){
        StringBuilder paramsStr = createPlaceholderArrays(itemList.size());
        conditionSql.append(TAG).append(paramName).append(TAG).append(SPLIT).append(IN).append(SPLIT)
                .append(LEFT_PARENTHESIS).append(paramsStr).append(RIGHT_PARENTHESIS).append(SPLIT);
        this.paramList.addAll(itemList);
        return this;
    }

    public ConditionSqlBase notIn(String paramName, List<Object> itemList){
        StringBuilder paramsStr = createPlaceholderArrays(itemList.size());
        conditionSql.append(TAG).append(paramName).append(TAG).append(SPLIT).append(NOT_IN).append(SPLIT)
                .append(LEFT_PARENTHESIS).append(paramsStr).append(RIGHT_PARENTHESIS).append(SPLIT);
        this.paramList.addAll(itemList);
        return this;
    }
    
    public ConditionSqlBase between(String paramName,Object start,Object end){
        betweenCondition(paramName,start,end,BETWEEN);
        return this;
    }

    public ConditionSqlBase notBetween(String paramName,Object start,Object end){
        betweenCondition(paramName,start,end,NOT_BETWEEN);
        return this;
    }

    private void betweenCondition(String paramName,Object start,Object end,String type){
        conditionSql.append(TAG).append(paramName).append(TAG).append(SPLIT)
                .append(type).append(SPLIT)
                .append(PLACEHOLDER).append(SPLIT)
                .append(AND).append(SPLIT)
                .append(PLACEHOLDER).append(SPLIT);
        this.paramList.add(start);
        this.paramList.add(end);
    }

    public ConditionSqlBase or(){
        conditionSql.append(OR).append(SPLIT);
        return this;
    }

    public ConditionSqlBase AND(){
        conditionSql.append(AND).append(SPLIT);
        return this;
    }


    /**
     * use subqueries
     */


    public ConditionSqlBase in(String params, Select childSqlBase){
        useSubqueries(params,childSqlBase,IN);
        return this;
    }

    public ConditionSqlBase notIn(String params, Select childSqlBase){
        useSubqueries(params,childSqlBase,NOT_IN);
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

    public ConditionSqlBase notEq(String params, Select childSqlBase){
        useSubqueries(params,childSqlBase,NOT_EQUAL);
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
