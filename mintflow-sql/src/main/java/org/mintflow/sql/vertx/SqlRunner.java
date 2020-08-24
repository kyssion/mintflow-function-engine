package org.mintflow.sql.vertx;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import org.mintflow.sql.Sql;
import org.mintflow.util.CollectionUtils;
import org.mintflow.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlRunner {
    public static interface Result{
        Sql doResult(RowSet<Row> rows);
    }

    boolean isStart;
    private MySQLPool mySQLPool;
    private boolean useTransaction = false;
    private Sql sql;
    private List<Result> results;
    int index=0;


    public static SqlRunner create(MySQLPool mySQLPool,Sql initStart,boolean useTransaction){
        SqlRunner sqlRunner = new SqlRunner(mySQLPool);
        sqlRunner.useTransaction = useTransaction;
        sqlRunner.results = new ArrayList<>();
        sqlRunner.sql = initStart;
        sqlRunner.index = 0;
        return sqlRunner;
    }

    public static SqlRunner create(MySQLPool mySQLPool,Sql initStart){
        return create(mySQLPool,initStart,false);
    }


    private SqlRunner(MySQLPool mySQLPool){
        this.mySQLPool = mySQLPool;
        this.isStart = false;
    }


    public SqlRunner result(Result result){
        this.results.add(result);
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
        if(index>=this.results.size()){
            sqlConnection.close();
            return;
        }
        Result result = this.results.get(index);
        index++;
        sqlConnection.preparedQuery(this.sql.getSql()).execute(
                Tuple.tuple(this.sql.getParamsList()),
                res->{
                    if(res.succeeded()){
                        this.sql = result.doResult(res.result());
                        doRun(sqlConnection);
                    }
                }
        );
    }

}
