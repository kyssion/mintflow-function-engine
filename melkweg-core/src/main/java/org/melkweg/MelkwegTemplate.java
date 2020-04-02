package org.melkweg;

import org.melkweg.template.MelkwegTemplateFunction;
import org.melkweg.template.proxy.MelkwegTemplateFunctionProxy;
import org.melkweg.util.ClassUtill;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MelkwegTemplate {

    private Map<Class<? extends MelkwegTemplateFunction>,MelkwegTemplateFunction> processProxy
            = new HashMap<>();
    private Melkweg melkweg;
    private MelkwegTemplate(){
        super();
    }

    public static MelkwegTemplate create(Melkweg melkweg,String...pkgName){
        MelkwegTemplate melkwegTemplate = new MelkwegTemplate();
        melkwegTemplate.melkweg = melkweg;
        for (String pkgItem: pkgName){
            Set<Class<?>> templateFunctionList =
                    ClassUtill.getClassSet(pkgItem,MelkwegTemplateFunction.class);
            for(Class<?> templateItem : templateFunctionList){
                if(MelkwegTemplateFunction.class.isAssignableFrom(templateItem)){
                    addTemplateFunction((Class<? extends MelkwegTemplateFunction>)templateItem,melkweg,melkwegTemplate);
                }
            }
        }
        return melkwegTemplate;
    }

    public static void addTemplateFunction(Class<? extends MelkwegTemplateFunction> funtion,Melkweg melkweg,MelkwegTemplate melkwegTemplate){
        MelkwegTemplateFunctionProxy<? extends MelkwegTemplateFunction>
                melkwegTemplateFunctionProxy = new MelkwegTemplateFunctionProxy<>(funtion,melkweg);
        MelkwegTemplateFunction melkwegTemplateFunction =
                (MelkwegTemplateFunction) Proxy.newProxyInstance(MelkwegTemplate.class.getClassLoader(),new Class[]{funtion},melkwegTemplateFunctionProxy);
        melkwegTemplate.processProxy.put(funtion,melkwegTemplateFunction);
    }

    public <T extends MelkwegTemplateFunction> T getTemplateFunction(Class<T> melkwegTemplateFunction){
        return (T) processProxy.get(melkwegTemplateFunction);
    }
}
