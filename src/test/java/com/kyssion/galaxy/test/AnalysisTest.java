package com.kyssion.galaxy.test;

import com.kyssion.galaxy.exception.AnalysisNoHandleException;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.StartHandler;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.scheduler.HandlerScheduler;
import com.kyssion.galaxy.script.translater.analysis.GrammaAnalysis;
import com.kyssion.galaxy.script.translater.analysis.LexicalAnalysis;
import com.kyssion.galaxy.script.translater.analysis.SemanticAnalysis;
import com.kyssion.galaxy.script.translater.data.error.ErrorInfoData;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.test.handler.TestHandleOne;
import com.kyssion.galaxy.test.handler.TestHandlerTwo;
import com.kyssion.galaxy.test.handler.reoader.ReoaderOne;
import com.kyssion.galaxy.test.handler.select.SelectOne;
import com.kyssion.galaxy.test.handler.select.SelectTwo;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisTest {
    public static void main(String[] args) throws IOException, AnalysisNoHandleException {
        System.out.println(new File("").getAbsolutePath());
        File file = new File("D:/project/java-project/Galaxy/src/test/resources/x.galaxy");
        LexicalAnalysis lexicalAnalysis = new LexicalAnalysis();
        List<LexicalAnalysisData> list = lexicalAnalysis.analysis(file);
        System.out.println(list.size());
        GrammaAnalysis grammaAnalysis = new GrammaAnalysis();
        int a = grammaAnalysis.analysis(list);

        if (a != list.size()) {
            if (grammaAnalysis.getTryItemDuque().size() != 0) {
                while (!grammaAnalysis.getTryItemDuque().isEmpty()) {
                    ErrorInfoData data = grammaAnalysis.getTryItemDuque().removeLast();
                    System.err.println("第"+data.getLineId()+"行" +
                            " "+"第" + data.getWordId()+"个字符"+"" +
                            " " + "字符:"+data.getValue() + " " +
                            "可能存在问题" + " " + data.getType().getDesc());
                }
            }
            return;
        }

        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();
        Map<String, Handle> stringHandleMap = new HashMap<>();
        stringHandleMap.put("one",new TestHandleOne());
        stringHandleMap.put("two",new TestHandlerTwo());
        stringHandleMap.put("rone",new ReoaderOne());
        stringHandleMap.put("sone",new SelectOne());
        stringHandleMap.put("stwo",new SelectTwo());
        int i = semanticAnalysis.analysis(list,stringHandleMap);
        Map<String, StartHandler> map = semanticAnalysis.getStartHandleMap();
        System.out.println();
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper = new HandlerScheduler().run(paramWrapper,map.get("namespace1.process1").getHandleList());
        System.out.println();
    }
}
