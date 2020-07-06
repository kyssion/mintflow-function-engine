package org.mintflow.vertx.http.adapter.request;

import io.vertx.core.json.Json;
import org.mintflow.annotation.MintFlowParam;
import org.mintflow.param.ParamWrapper;
import org.mintflow.util.MintFlowStrUtil;
import org.mintflow.vertx.http.controller.MintFlowMapperParam;
import org.mintflow.vertx.http.exceptrion.MintFlowControllerError;
import org.mintflow.vertx.http.param.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ControllerMapperParamAdapter extends DefaultRequestParamAdapter {

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
            if (mintFlowParam == null) {
                throw new MintFlowControllerError("参数必须要有MintFlowMapperParam");
            }

            MapperParamRule mapperParamRule = new MapperParamRule();
            RuleType fromRule = mintFlowParam.fromType();
            String fromName = mintFlowParam.fromName();

            if (MintFlowStrUtil.isNullOrEmpty(fromName)&&fromRule!=RuleType.FROM_BODY) {
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

            types.add(mapperParamRule);
        }
    }

    @Override
    public ParamWrapper createParams(RequestParam t) {
        ParamWrapper paramWrapper = super.createParams(t);
        for (MapperParamRule mapperParamRule : types) {
            MapperParamRule mapper = new MapperParamRule();
            Object needItem = null;
            switch (mapper.fromRule) {
                case FROM_BODY:
                    needItem = Json.decodeValue(t.getBody(), mapper.toType);
                    break;
                case FROM_FORM:
                    needItem = t.getFormAttributes().get(mapper.getFromName());
                    break;
                case FROM_PARAMS:
                    needItem = t.getParams().get(mapper.getFromName());
                    break;
                default:
                    break;
            }

            switch (mapper.toRule) {
                case TO_NAME:
                    paramWrapper.setContextParam(mapper.getToName(), needItem);
                    break;
                case TO_TYPE:
                    paramWrapper.setParam(mapper.toType, needItem);
                    break;
            }


        }
        return paramWrapper;
    }
}
