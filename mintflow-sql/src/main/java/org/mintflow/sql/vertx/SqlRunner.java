package org.mintflow.sql.vertx;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import org.mintflow.sql.Sql;

import java.util.ArrayList;
import java.util.List;

public class SqlRunner {
    public static interface Result{
        Sql doResult(RowSet<Row> rows);
    }

    private List<Result> results;
    private List<Sql> sqlList;
    int index = 0;
    boolean isStart;
    private MySQLPool mySQLPool;
    private boolean useTransaction = false;

    public SqlRunner(MySQLPool mySQLPool){
        this.mySQLPool = mySQLPool;
        this.results = new ArrayList<>();
        this.sqlList = new ArrayList<>();
        this.isStart = false;
    }

    public SqlRunner addSql(Sql sql,Result runner){
        this.sqlList.add(sql);
        this.results.add(runner);
        return this;
    }

    public SqlRunner transaction(){
        this.useTransaction = true;
        return this;
    }

    public void run(){
        this.mySQLPool.getConnection(con->{
            if(con.succeeded()){
                SqlConnection sqlConnection = con.result();
                if(useTransaction){
                    sqlConnection.begin();
                }
                doRun(sqlConnection);
            }
        });
    }

    private void doRun(SqlConnection sqlConnection){
        if(index>=this.sqlList.size()||index>=this.results.size()){
            sqlConnection.close();
            return;
        }
        Sql sql = this.sqlList.get(index);
        Result result = this.results.get(index);
        index++;
        sqlConnection.preparedQuery(sql.getSql()).execute(
                Tuple.tuple(sql.getParamsList()),
                res->{
                    if(res.succeeded()){
                        result.doResult(res.result());
                        doRun(sqlConnection);
                    }
                }
        );
    }
}
