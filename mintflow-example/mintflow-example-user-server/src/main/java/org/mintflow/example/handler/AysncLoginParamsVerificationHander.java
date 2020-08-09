package org.mintflow.example.handler;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Tuple;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.example.bean.User;
import org.mintflow.example.sqlBuiler.UserSqlBuilder;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.sql.Select;
import org.mintflow.sql.Sql;

@MintFlowHandler(name = "login-params-verification")
public class AysncLoginParamsVerificationHander extends AsyncSampleFnHandler {

    public AysncLoginParamsVerificationHander(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        User user = params.getParam(User.class);
        MySQLPool mySQLPool = params.getParam(MySQLPool.class);
        Sql sql = UserSqlBuilder.selectUser(user);
        mySQLPool.preparedQuery(sql.getSql()).execute(
                Tuple.tuple(sql.getParamsList()),
                res->{
                    res.
                }
        );
    }
}
