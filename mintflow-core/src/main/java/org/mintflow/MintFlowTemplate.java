package org.mintflow;

import org.mintflow.template.MintFlowTemplateFunction;
import org.mintflow.template.proxy.MintFlowTemplateFunctionProxy;
import org.mintflow.util.ClassFindUtil;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MintFlowTemplate {

    public static class MintFlowTemplateBuilder{

        private Map<Class<? extends MintFlowTemplateFunction>,MintFlowTemplateFunction> processProxy = new HashMap<>();

        private MintFlow MintFlow;

        @SuppressWarnings("unchecked")
        public MintFlowTemplateBuilder addInterface(MintFlow MintFlow,String...pkgName){
            this.MintFlow = MintFlow;
            for (String pkgItem: pkgName){
                Set<Class<?>> templateFunctionList =
                        ClassFindUtil.getClassSet(pkgItem,MintFlowTemplateFunction.class);
                for(Class<?> templateItem : templateFunctionList){
                    if(MintFlowTemplateFunction.class.isAssignableFrom(templateItem)){
                        addTemplateFunction((Class<? extends MintFlowTemplateFunction>)templateItem,MintFlow);
                    }
                }
            }
            return this;
        }

        public void addTemplateFunction(Class<? extends MintFlowTemplateFunction> funtion,MintFlow MintFlow){
            MintFlowTemplateFunctionProxy<? extends MintFlowTemplateFunction>
                    MintFlowTemplateFunctionProxy = new MintFlowTemplateFunctionProxy<>(funtion,MintFlow);
            MintFlowTemplateFunction MintFlowTemplateFunction =
                    (MintFlowTemplateFunction) Proxy.newProxyInstance(MintFlowTemplate.class.getClassLoader(),
                            new Class[]{funtion},MintFlowTemplateFunctionProxy);
            processProxy.put(funtion,MintFlowTemplateFunction);
        }

        public MintFlowTemplate build(){
            MintFlowTemplate MintFlowTemplate = new MintFlowTemplate();
            MintFlowTemplate.processProxy = this.processProxy;
            MintFlowTemplate.MintFlow = this.MintFlow;
            return MintFlowTemplate;
        }
    }

    private Map<Class<? extends MintFlowTemplateFunction>,MintFlowTemplateFunction> processProxy
            = new HashMap<>();

    private MintFlow MintFlow;

    private MintFlowTemplate(){
        super();
    }

    @SuppressWarnings("unchecked")
    public <T extends MintFlowTemplateFunction> T getTemplateFunction(Class<T> MintFlowTemplateFunction){
        return (T) processProxy.get(MintFlowTemplateFunction);
    }

    public static MintFlowTemplateBuilder newBuilder(){
        return new MintFlowTemplateBuilder();
    }
}
