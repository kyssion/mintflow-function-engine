package org.melkweg;

import org.melkweg.template.MelkwegTemplateFuntion;

import java.util.HashMap;
import java.util.Map;

public class MelkwegTemplate {

    private Map<Class<? extends MelkwegTemplateFuntion>, MelkwegTemplateFuntion> processProxy
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

    public void addTemplateFunction(MelkwegTemplateFuntion funtion){
        this.processProxy.put(funtion.getClass(),funtion);
    }
}
