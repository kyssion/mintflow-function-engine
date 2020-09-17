package org.mintflow.vertx.http.adapter.request;

import io.vertx.core.json.Json;
import org.mintflow.param.ParamWrapper;
import org.mintflow.util.StringUtil;
import org.mintflow.vertx.http.controller.*;
import org.mintflow.vertx.http.exceptrion.MintFlowControllerError;
import org.mintflow.vertx.http.param.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ControllerMapperParamAdapter implements RequestParamAdapter {

    List<MapperParamRule> types = new ArrayList<>();

    public enum RuleType {
        FROM_PARAMS, FROM_FORM, FROM_BODY
    }

    public static class MapperParamRule {
        //数据的获取途径
        private RuleType fromRule;
        //与From_name配套，描述获取的表单数据
        private String fromName;
        //param中的类型
        private String toName;

        private boolean toType;
        private Class<?> itemClass;


        public RuleType getFromRule() {
            return fromRule;
        }

        public boolean isToType() {
            return toType;
        }

        public void setToType(boolean toType) {
            this.toType = toType;
        }

        public Class<?> getItemClass() {
            return itemClass;
        }

        public void setItemClass(Class<?> itemClass) {
            this.itemClass = itemClass;
        }

        public void setFromRule(RuleType fromRule) {
            this.fromRule = fromRule;
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

    }

    public void addTypes(MapperParamRule mapperParamRule) {
        this.types.add(mapperParamRule);
    }

    @Override
    public void initAdapter(Method method) {
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            ParamFromBody paramFromBody = parameter.getAnnotation(ParamFromBody.class);
            ParamFromForm paramFromForm = parameter.getAnnotation(ParamFromForm.class);
            ParamFromUrl paramFromUrl = parameter.getAnnotation(ParamFromUrl.class);
            if(!isOnly(paramFromForm,paramFromBody,paramFromUrl)){
                throw new MintFlowControllerError("方法"+method.getName()+",参数"+parameter.getName()+"中,  ParamFromBody ParamFromForm ParamFromUrl 注解, 有且只能唯一");
            }
            MapperParamRule mapperParamRule = new MapperParamRule();
            mapperParamRule.setItemClass(parameter.getType());

            if(paramFromBody!=null){
                mapperParamRule.setFromName("");
                mapperParamRule.setFromRule(RuleType.FROM_BODY);
                if(paramFromBody.toTyp()){
                    mapperParamRule.setToName("");
                    mapperParamRule.setToType(true);
                }else{
                    String name = paramFromBody.toName();
                    if(StringUtil.isNullOrEmpty(name)){
                        throw new MintFlowControllerError("方法"+method.getName()+",参数"+parameter.getName()+"中,ParamFromBody注解 toName 参数不可为空  或者 toType = true" );
                    }
                    mapperParamRule.setToName(name);
                }
            }

            if(paramFromForm!=null){
                mapperParamRule.setFromName(paramFromForm.fromName());
                mapperParamRule.setFromRule(RuleType.FROM_FORM);
                if(paramFromForm.toTyp()){
                    mapperParamRule.setToName("");
                    mapperParamRule.setToType(true);
                }else{
                    String name = paramFromForm.toName();
                    if(StringUtil.isNullOrEmpty(name)){
                        throw new MintFlowControllerError("方法"+method.getName()+",参数"+parameter.getName()+"中,ParamFromBody注解 toName 参数不可为空  或者 toType = true" );
                    }
                    mapperParamRule.setToName(name);
                }
            }

            if(paramFromUrl !=null){
                mapperParamRule.setFromName(paramFromUrl.fromName());
                mapperParamRule.setFromRule(RuleType.FROM_PARAMS);
                if(paramFromUrl.toTyp()){
                    mapperParamRule.setToName("");
                    mapperParamRule.setToType(true);
                }else{
                    String name = paramFromUrl.toName();
                    if(StringUtil.isNullOrEmpty(name)){
                        throw new MintFlowControllerError("方法"+method.getName()+",参数"+parameter.getName()+"中,ParamFromBody注解 toName 参数不可为空 或者 toType = true" );
                    }
                    mapperParamRule.setToName(name);
                }
            }


            types.add(mapperParamRule);
        }
    }

    private boolean isOnly(ParamFromForm paramFromForm,ParamFromBody paramFromBody,ParamFromUrl paramFromUrl){
        int index = 0;
        if(paramFromBody!=null){
            index++;
        }
        if(paramFromForm!=null){
            index++;
        }
        if(paramFromUrl!=null){
            index++;
        }

        return index==1;
    }

    @Override
    public ParamWrapper createParams(RequestParam t) {
        ParamWrapper paramWrapper = new ParamWrapper();
        for (MapperParamRule mapperParamRule : types) {
            Object needItem = null;
            switch (mapperParamRule.fromRule) {
                case FROM_BODY:
                    String body = t.getBody();
                    if(!StringUtil.isNullOrEmpty(body)){
                        needItem = Json.decodeValue(t.getBody(), mapperParamRule.getItemClass());
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
            if(mapperParamRule.isToType()){
                paramWrapper.setParam(mapperParamRule.getItemClass(), needItem);
            }else{
                paramWrapper.setContextParam(mapperParamRule.getToName(), needItem);
            }
        }
        return paramWrapper;
    }
}
