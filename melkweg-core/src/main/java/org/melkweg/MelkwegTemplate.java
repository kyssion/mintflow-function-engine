package org.melkweg;

import org.melkweg.template.MelkwegTemplateFunction;
import org.melkweg.template.proxy.MelkwegTemplateFunctionProxy;
import org.melkweg.util.ClassUtill;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MelkwegTemplate {

    public static class MelkwegTemplateBuilder{

        private Map<Class<? extends MelkwegTemplateFunction>,MelkwegTemplateFunction> processProxy = new HashMap<>();

        private Melkweg melkweg;

        @SuppressWarnings("unchecked")
        public MelkwegTemplateBuilder addInterface(Melkweg melkweg,String...pkgName){
            this.melkweg = melkweg;
            for (String pkgItem: pkgName){
                Set<Class<?>> templateFunctionList =
                        ClassUtill.getClassSet(pkgItem,MelkwegTemplateFunction.class);
                for(Class<?> templateItem : templateFunctionList){
                    if(MelkwegTemplateFunction.class.isAssignableFrom(templateItem)){
                        addTemplateFunction((Class<? extends MelkwegTemplateFunction>)templateItem,melkweg);
                    }
                }
            }
            return this;
        }

        public void addTemplateFunction(Class<? extends MelkwegTemplateFunction> funtion,Melkweg melkweg){
            MelkwegTemplateFunctionProxy<? extends MelkwegTemplateFunction>
                    melkwegTemplateFunctionProxy = new MelkwegTemplateFunctionProxy<>(funtion,melkweg);
            MelkwegTemplateFunction melkwegTemplateFunction =
                    (MelkwegTemplateFunction) Proxy.newProxyInstance(MelkwegTemplate.class.getClassLoader(),
                            new Class[]{funtion},melkwegTemplateFunctionProxy);
            processProxy.put(funtion,melkwegTemplateFunction);
        }

        public MelkwegTemplate build(){
            MelkwegTemplate melkwegTemplate = new MelkwegTemplate();
            melkwegTemplate.processProxy = this.processProxy;
            melkwegTemplate.melkweg = this.melkweg;
            return melkwegTemplate;
        }
    }

    private Map<Class<? extends MelkwegTemplateFunction>,MelkwegTemplateFunction> processProxy
            = new HashMap<>();

    private Melkweg melkweg;

    private MelkwegTemplate(){
        super();
    }

    @SuppressWarnings("unchecked")
    public <T extends MelkwegTemplateFunction> T getTemplateFunction(Class<T> melkwegTemplateFunction){
        return (T) processProxy.get(melkwegTemplateFunction);
    }

    public static MelkwegTemplateBuilder newBuilder(){
        return new MelkwegTemplateBuilder();
    }
}
