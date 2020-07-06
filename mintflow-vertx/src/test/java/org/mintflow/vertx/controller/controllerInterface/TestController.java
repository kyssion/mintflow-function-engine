package org.mintflow.vertx.controller.controllerInterface;

import org.mintflow.vertx.controller.bean.DefaultResponseBean;
import org.mintflow.vertx.http.controller.MintFlowController;
import org.mintflow.vertx.http.controller.MintFlowMapperParam;

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
    DefaultResponseBean test(@MintFlowMapperParam(fromName = "name") String name,
                             @MintFlowMapperParam() DefaultResponseBean defaultResponseBean);
}
