package org.mintflow.vertx.sql.mysql;

public class ConditionSqlBase extends SqlBase {

    /**
     * equal
     * @param params
     * @param item
     * @return
     */
    public ConditionSqlBase eq(String params, Object item){

        return this;
    }

    public ConditionSqlBase eq(String params, ConditionSqlBase childSqlBase){

        return this;
    }

    /**
     * greater than
     * @param params
     * @param childSqlBase
     * @return
     */
    public ConditionSqlBase gt(String params , ConditionSqlBase childSqlBase){
        return this;
    }

    public ConditionSqlBase gt(String params, Object item){
        return this;
    }

    /**
     * greater than or equal to
     * @return
     */
    public ConditionSqlBase gte(String params, Select childSql){
        return this;
    }
    public ConditionSqlBase gte(String params, Object item){
        return this;
    }

    /**
     * Less than
     * @return
     */
    public ConditionSqlBase lt(String params, Select childSql){
        return this;
    }
    public ConditionSqlBase lt(String params, Object item){
        return this;
    }

    /**
     * Less than or equal to
     * @return
     */
    public ConditionSqlBase ltq(String params, Select childSql){
        return this;
    }
    public ConditionSqlBase ltq(String params, Object item){
        return this;
    }


    public ConditionSqlBase like(String name, String item){
        return this;
    }

    public ConditionSqlBase notLike(String name, String item){
        return this;
    }
    public ConditionSqlBase likeLeft(String name, String item){
        return this;
    }
    public ConditionSqlBase likeRight(String name, String item){
        return this;
    }
    public ConditionSqlBase isNull(String name){
        return this;
    }
    public ConditionSqlBase isNotNull(String name){
        return this;
    }
}
