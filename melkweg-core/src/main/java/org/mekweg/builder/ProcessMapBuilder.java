package org.mekweg.builder;

import org.mekweg.annotation.ProcessNameSpace;
import org.mirror.reflection.Reflector;
import org.mirror.reflection.io.ClassFindUtil;
import org.mekweg.process.Process;

import java.util.*;

public class ProcessMapBuilder {

    /**
     * build process map
     *
     * @param processPath
     * @return
     */
    public static Map<String, Class<? extends Process>> build(String... processPath) {
        Map<String, Class<? extends Process>> map = new HashMap<>();
        Set<Class<? extends Process>> allProcessSet= new HashSet<>();
        for (String process: processPath){
            allProcessSet.addAll((Collection<? extends Class<? extends Process>>) ClassFindUtil.getClassSet(process));
        }
        allProcessSet.forEach((process) -> {
            Reflector reflector = new Reflector(process);
            ProcessNameSpace processerAnno = reflector.getAnnotation(ProcessNameSpace.class);
            if(processerAnno!=null){
                map.put(processerAnno.value(),process);
            }else{
                map.put(reflector.getClassName(),process);
            }
        });
        return map;
    }
}
