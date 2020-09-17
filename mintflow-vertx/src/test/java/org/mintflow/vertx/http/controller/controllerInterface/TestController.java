package org.mintflow.vertx.http.controller.controllerInterface;

import org.mintflow.vertx.http.controller.ParamFromBody;
import org.mintflow.vertx.http.controller.ParamFromUrl;
import org.mintflow.vertx.http.controller.bean.DefaultRequestBean;
import org.mintflow.vertx.http.controller.bean.DefaultResponseBean;
import org.mintflow.vertx.http.controller.MintFlowController;
import org.mintflow.vertx.http.controller.MintFlowRequestMapper;

@MintFlowController(url="test_controller",nameSpace = "test_controller")
public interface TestController {
    /**
     * 测试自动化配置接口
     * @param name
     * @param defaultResponseBean
     * @return
     */
    @MintFlowRequestMapper(url = "test_process",process = "test_process")
    DefaultResponseBean test(@ParamFromUrl(fromName = "name",toTyp = true) String name,
                             @ParamFromBody DefaultRequestBean defaultResponseBean);
}
