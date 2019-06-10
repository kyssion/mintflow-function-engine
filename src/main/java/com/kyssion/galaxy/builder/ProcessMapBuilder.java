package com.kyssion.galaxy.builder;

import com.kyssion.galaxy.annotation.Processer;
import com.kyssion.galaxy.process.Process;
import org.mirror.reflection.io.ClassFindleUtil;
import org.mirror.reflection.mirror.MirrorClass;

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
            MirrorClass handerClass = MirrorClass.forClass(process);
            Processer processerAnno = handerClass.getAnnotation(Processer.class);
            if(processerAnno!=null){
                map.put(processerAnno.id(),process);
            }else{
                map.put(handerClass.getClassName(),process);
            }
        });
        return map;
    }
}
