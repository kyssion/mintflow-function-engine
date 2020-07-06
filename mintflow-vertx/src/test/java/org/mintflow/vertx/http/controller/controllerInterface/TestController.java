package org.mintflow.vertx.http.controller.controllerInterface;

import org.mintflow.vertx.http.controller.bean.DefaultResponseBean;
import org.mintflow.vertx.http.adapter.request.ControllerMapperParamAdapter;
import org.mintflow.vertx.http.controller.MintFlowController;
import org.mintflow.vertx.http.controller.MintFlowMapperParam;
import org.mintflow.vertx.http.controller.MintFlowRequestMapper;

@MintFlowController(
        url="test_controller",
        nameSpace = "test_controller"
)
public interface TestController {
    /**
     * 测试自动化配置接口
     * @param name
     * @param defaultResponseBean
     * @return
     */
    @MintFlowRequestMapper(url = "test1",process = "test_process")
    DefaultResponseBean test(@MintFlowMapperParam(fromName = "name") String name,
                             @MintFlowMapperParam(fromType = ControllerMapperParamAdapter.RuleType.FROM_BODY) DefaultResponseBean defaultResponseBean);
}
