package org.galaxy.builder;

import org.galaxy.annotation.ProcessNameSpace;
import org.mirror.reflection.Reflector;
import org.mirror.reflection.io.ClassFindleUtil;
import org.galaxy.process.Process;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcessMapBuilder {

    /**
     * build process map
     *
     * @param processPath
     * @return
     */
    public static Map<String, Class<? extends Process>> build(String... processPath) {
        Map<String, Class<? extends Process>> map = new HashMap<>();
        ClassFindleUtil<Process> handleResolverUtil = new ClassFindleUtil<>();
        handleResolverUtil.findImplementations(Process.class, processPath);
        Set<Class<? extends Process>> processSet = handleResolverUtil.getClasses();
        processSet.forEach((process) -> {
            Reflector reflector = new Reflector(process);
            ProcessNameSpace processerAnno = reflector.getAnnotation(ProcessNameSpace.class);
            if(processerAnno!=null){
                map.put(processerAnno.id(),process);
            }else{
                map.put(reflector.getClassName(),process);
            }
        });
        return map;
    }
}
