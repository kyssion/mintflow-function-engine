package org.mintflow.vertx.sql.mysql;

public class SqlCreator {
    private StringBuilder stringBuilder;
    private SqlCreator(){
        super();
    }
    public Select selectFrom(String tableName,String...params){
        SqlCreator sqlCreator = new SqlCreator();
        Select select = new Select();
        select.selectFrom(tableName,params);
        return select;
    }
}
