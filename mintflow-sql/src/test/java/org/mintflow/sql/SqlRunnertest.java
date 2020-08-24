package org.mintflow.sql;

import io.vertx.mysqlclient.MySQLPool;
import org.mintflow.sql.vertx.SqlRunner;

public class SqlRunnertest {

    public void test(){
        SqlRunner sqlRunner = SqlRunner.create(MySQLPool.pool(""),new Sql("",null));
        sqlRunner.result((row)->{
            return new Sql("",null);
        }).run();
    }
}
