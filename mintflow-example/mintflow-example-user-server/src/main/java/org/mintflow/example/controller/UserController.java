package org.mintflow.example.controller;

import io.vertx.core.http.HttpMethod;
import org.mintflow.example.bean.User;
import org.mintflow.vertx.http.controller.MintFlowController;
import org.mintflow.vertx.http.controller.MintFlowRequestMapper;

@MintFlowController(
        url = "user",
        nameSpace = "user-server"
)
public interface UserController {
    @MintFlowRequestMapper(
            url = "login",
            process = "user-login",
            httpMethod = {HttpMethod.GET,HttpMethod.POST}
    )
    User login(User user);
}
