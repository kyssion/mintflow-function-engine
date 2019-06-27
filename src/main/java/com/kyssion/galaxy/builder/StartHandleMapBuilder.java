package com.kyssion.galaxy.builder;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.header.StartHandler;
import com.kyssion.galaxy.script.ScriptAnalysis;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StartHandleMapBuilder {

    public static Map<String, StartHandler> build(Map<String, Handle> handleMap, String... paths) throws IOException {
        Map<String,StartHandler> startHanderMap = new HashMap<>();
        for (String path : paths) {
            URL uri = StartHandler.class.getClassLoader().getResource("x.gal");
            if(uri==null){
                continue;
            }
            File file = new File(uri.getFile());
            startHanderMap.putAll(ScriptAnalysis.analysis(file,handleMap));
        }
        return startHanderMap;
    }
}
