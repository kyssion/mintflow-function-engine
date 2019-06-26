package com.kyssion.galaxy.builder;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.header.StartHander;
import com.kyssion.galaxy.script.ScriptAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StartHandleMapBuilder {

    public static Map<String, StartHander> build(Map<String, Handle> handleMap, String... paths) throws IOException {
        Map<String,StartHander> startHanderMap = new HashMap<>();
        for (String path : paths) {
            File file = new File(path);
            startHanderMap.putAll(ScriptAnalysis.analysis(file,handleMap));
        }
        return startHanderMap;
    }
}
