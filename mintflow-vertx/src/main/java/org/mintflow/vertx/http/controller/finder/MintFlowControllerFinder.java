package org.mintflow.vertx.http.controller.finder;

import io.vertx.core.http.HttpMethod;
import org.mintflow.util.ClassFindUtil;
import org.mintflow.util.StringUtil;
import org.mintflow.vertx.http.adapter.request.RequestParamAdapter;
import org.mintflow.vertx.http.adapter.response.ControllerMapperResponseAdapter;
import org.mintflow.vertx.http.adapter.response.ResponseParamAdapter;
import org.mintflow.vertx.http.controller.MintFlowController;
import org.mintflow.vertx.http.controller.MintFlowRequestMapper;
import org.mintflow.vertx.http.exceptrion.MintFlowControllerError;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MintFlowControllerFinder {
    public static List<FinderItem> find(String...pkNames){
        List<FinderItem> finderItems = new ArrayList<>();
        Set<Class<?>> classSet = new HashSet<>();
        for(String pkName:pkNames){
            classSet.addAll(ClassFindUtil.getClassSet(pkName));
        }
        for(Class<?> cls : classSet){
            MintFlowController controller =
                    cls.getAnnotation(MintFlowController.class);
            if(controller==null){
                continue;
            }
            String url = controller.url();
            String nameSpace = controller.nameSpace();
            if(StringUtil.isNullOrEmpty(url,nameSpace)){
                continue;
            }
            Method[] methods = cls.getMethods();
            for(Method method: methods){
                FinderItem finderItem = createFinder(url,nameSpace,method);
                if(finderItem==null){
                    continue;
                }
                finderItems.add(finderItem);
            }
        }
        return finderItems;
    }

    private static FinderItem createFinder(String parentUrl, String nameSpace, Method method) {
        MintFlowRequestMapper mapper = method.getAnnotation(MintFlowRequestMapper.class);
        if(mapper==null){
            return null;
        }
        String url = mapper.url();
        if(StringUtil.isNullOrEmpty(url)){
            throw new MintFlowControllerError("URL不可为空");
        }
        parentUrl = StringUtil.dealWithUrl(parentUrl);
        url = StringUtil.dealWithUrl(url);
        String process = mapper.process();
        HttpMethod[] httpMethods = mapper.httpMethod();
        RequestParamAdapter requestParamAdapter = getRequestParamAdapter(mapper,method);
        ResponseParamAdapter responseParamAdapter = new ControllerMapperResponseAdapter(method.getReturnType());
        FinderItem finderItem = new FinderItem();
        finderItem.setNameSpace(nameSpace);
        finderItem.setProcess(process);
        finderItem.setUrl(parentUrl+url);
        finderItem.setHttpMethod(httpMethods);
        finderItem.setRequestParamAdapter(requestParamAdapter);
        finderItem.setResponseParamAdapter(responseParamAdapter);
        return finderItem;
    }

    private static RequestParamAdapter getRequestParamAdapter(MintFlowRequestMapper mapper, Method method) {
        Class<? extends RequestParamAdapter> paramAdapterClass =
                mapper.requestParamAdapter();
        try {
            RequestParamAdapter requestParamAdapter = paramAdapterClass.getConstructor().newInstance();
            requestParamAdapter.initAdapter(method);
            return requestParamAdapter;
        } catch (Exception e){
            e.printStackTrace();
            throw new MintFlowControllerError("请求参数处理器初始化失败："+paramAdapterClass.getName());
        }
    }
}
