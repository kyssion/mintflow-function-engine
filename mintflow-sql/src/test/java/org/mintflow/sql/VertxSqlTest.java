package org.mintflow.sql;

import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class VertxSqlTest {
    public void vertxTest() {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3306)
                .setHost("the-host")
                .setDatabase("the-db")
                .setUser("user")
                .setPassword("secret");

        // Pool options
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        // Create the client pool
        MySQLPool client = MySQLPool.pool(connectOptions, poolOptions);

        // A simple query
        client.query("SELECT * FROM users WHERE id='julien'").execute(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> result = ar.result();
                System.out.println("Got " + result.size() + " rows ");
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
            // Now close the pool
            client.close();
        });
        client.preparedQuery("select * from user where name = 1").execute(Tuple.tuple(),(res)->{
            res.result();
        });
    }
}
