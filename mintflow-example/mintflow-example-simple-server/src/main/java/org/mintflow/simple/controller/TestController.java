package org.mintflow.simple.controller;

import io.vertx.core.http.HttpMethod;
import org.mintflow.simple.bean.Buy;
import org.mintflow.simple.bean.ResMsg;
import org.mintflow.vertx.http.adapter.request.ControllerMapperParamAdapter;
import org.mintflow.vertx.http.controller.MintFlowController;
import org.mintflow.vertx.http.controller.MintFlowMapperParam;
import org.mintflow.vertx.http.controller.MintFlowRequestMapper;

@MintFlowController(
        url="buy",
        nameSpace = "buy"
)
public interface TestController {
    /**
     * 买东西接口
     * @return
     */
    @MintFlowRequestMapper(url = "start",process = "buy_process",httpMethod = HttpMethod.POST)
    ResMsg test(@MintFlowMapperParam(fromType = ControllerMapperParamAdapter.RuleType.FROM_BODY) Buy buy);
}
