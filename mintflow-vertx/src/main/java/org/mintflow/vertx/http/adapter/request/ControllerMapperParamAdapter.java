package org.mintflow.vertx.http.adapter.request;

import io.vertx.core.json.Json;
import org.mintflow.param.ParamWrapper;
import org.mintflow.util.MintFlowStrUtil;
import org.mintflow.vertx.http.controller.MintFlowMapperBody;
import org.mintflow.vertx.http.controller.MintFlowMapperParam;
import org.mintflow.vertx.http.exceptrion.MintFlowControllerError;
import org.mintflow.vertx.http.param.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ControllerMapperParamAdapter implements RequestParamAdapter {

    List<MapperParamRule> types = new ArrayList<>();

    public enum RuleType {
        FROM_PARAMS, FROM_FORM, FROM_BODY, TO_NAME, TO_TYPE
    }

    public static class MapperParamRule {
        //数据的获取途径
        private RuleType fromRule;
        //数据的存入Param的途径
        private RuleType toRule;
        //与From_name配套，描述获取的表单数据
        private String fromName;

        //param中的类型
        private String toName;
        private Class<?> toType;

        public RuleType getFromRule() {
            return fromRule;
        }

        public void setFromRule(RuleType fromRule) {
            this.fromRule = fromRule;
        }

        public RuleType getToRule() {
            return toRule;
        }

        public void setToRule(RuleType toRule) {
            this.toRule = toRule;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getToName() {
            return toName;
        }

        public void setToName(String toName) {
            this.toName = toName;
        }

        public Class<?> getToType() {
            return toType;
        }

        public void setToType(Class<?> toType) {
            this.toType = toType;
        }
    }

    public void addTypes(MapperParamRule mapperParamRule) {
        this.types.add(mapperParamRule);
    }

    @Override
    public void initAdapter(Method method) {
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            MintFlowMapperParam mintFlowParam =
                    parameter.getAnnotation(MintFlowMapperParam.class);
            MintFlowMapperBody mintFlowMapperBody =
                    parameter.getAnnotation(MintFlowMapperBody.class);

            if (mintFlowParam != null&&mintFlowMapperBody!=null) {
                throw new MintFlowControllerError("MintFlowMapperParam和MintFlowMapperBody注解只能二选一");
            }
            MapperParamRule mapperParamRule = new MapperParamRule();
            if(mintFlowParam!=null) {
                RuleType fromRule = mintFlowParam.fromType();
                String fromName = mintFlowParam.fromName();

                if (MintFlowStrUtil.isNullOrEmpty(fromName) && fromRule != RuleType.FROM_BODY) {
                    throw new MintFlowControllerError("MintFlowParam fromName 当是非body类型的时候,字段不可为空");
                }
                //init base params
                mapperParamRule.setFromName(fromName);
                mapperParamRule.setFromRule(fromRule);
                mapperParamRule.setToType(parameter.getType());

                String toName = mintFlowParam.toName();
                mapperParamRule.setToName(toName);
                //init to type
                if (!MintFlowStrUtil.isNullOrEmpty(toName)) {
                    mapperParamRule.setToRule(RuleType.TO_NAME);
                } else {
                    mapperParamRule.setToRule(RuleType.TO_TYPE);
                }
            }else{
                mapperParamRule.setFromName("");
                mapperParamRule.setFromRule(RuleType.FROM_BODY);
                mapperParamRule.setToType(parameter.getType());
                mapperParamRule.setToRule(RuleType.TO_TYPE);
                mapperParamRule.setToName("");
            }
            types.add(mapperParamRule);
        }
    }

    @Override
    public ParamWrapper createParams(RequestParam t) {
        ParamWrapper paramWrapper = new ParamWrapper();
        for (MapperParamRule mapperParamRule : types) {
            Object needItem = null;
            switch (mapperParamRule.fromRule) {
                case FROM_BODY:
                    String body = t.getBody();
                    if(!MintFlowStrUtil.isNullOrEmpty(body)){
                        needItem = Json.decodeValue(t.getBody(), mapperParamRule.toType);
                    }
                    break;
                case FROM_FORM:
                    needItem = t.getFormAttributes().get(mapperParamRule.getFromName());
                    break;
                case FROM_PARAMS:
                    needItem = t.getParams().get(mapperParamRule.getFromName());
                    break;
                default:
                    break;
            }

            switch (mapperParamRule.toRule) {
                case TO_NAME:
                    paramWrapper.setContextParam(mapperParamRule.getToName(), needItem);
                    break;
                case TO_TYPE:
                    paramWrapper.setParam(mapperParamRule.toType, needItem);
                    break;
            }


        }
        return paramWrapper;
    }
}
