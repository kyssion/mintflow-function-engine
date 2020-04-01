package org.melkweg;

import org.melkweg.template.MelkwegTemplateFunction;
import org.melkweg.template.proxy.MelkwegTemplateFunctionProxy;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MelkwegTemplate {

    private Map<Class<? extends MelkwegTemplateFunction>,MelkwegTemplateFunction> processProxy
            = new HashMap<>();
    private Melkweg melkweg;
    private MelkwegTemplate(){
        super();
    }

    MelkwegTemplate create(Melkweg melkweg,String...pkgName){
        MelkwegTemplate melkwegTemplate = new MelkwegTemplate();
        melkwegTemplate.melkweg = melkweg;
        for (String pkgItem: pkgName){

        }
        return melkwegTemplate;
    }

    public void addTemplateFunction(Class<? extends MelkwegTemplateFunction> funtion){
        MelkwegTemplateFunctionProxy<? extends MelkwegTemplateFunction>
                melkwegTemplateFunctionProxy = new MelkwegTemplateFunctionProxy<>(funtion,melkweg);
        MelkwegTemplateFunction melkwegTemplateFunction =
                (MelkwegTemplateFunction) Proxy.newProxyInstance(MelkwegTemplate.class.getClassLoader(),new Class[]{funtion},melkwegTemplateFunctionProxy);
        this.processProxy.put(funtion,melkwegTemplateFunction);
    }

    public <T extends MelkwegTemplateFunction> T getTemplateFunction(Class<T> melkwegTemplateFunction){
        return (T) processProxy.get(melkwegTemplateFunction);
    }
}
