package org.mekweg.test;

import org.mekweg.exception.AnalysisNoHandleException;
import org.mekweg.handle.Handle;
import org.mekweg.handle.StartHandler;
import org.mekweg.param.ParamWrapper;
import org.mekweg.scheduler.HandlerScheduler;
import org.mekweg.script.translater.analysis.GrammaAnalysis;
import org.mekweg.script.translater.analysis.LexicalAnalysis;
import org.mekweg.script.translater.analysis.SemanticAnalysis;
import org.mekweg.script.translater.data.error.ErrorInfoData;
import org.mekweg.script.translater.data.workKeyData.LexicalAnalysisData;
import org.mekweg.test.handler.TestHandleOne;
import org.mekweg.test.handler.TestHandlerTwo;
import org.mekweg.test.handler.reoader.ReoaderOne;
import org.mekweg.test.handler.select.SelectOne;
import org.mekweg.test.handler.select.SelectTwo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisTest {
    public static void main(String[] args) throws IOException, AnalysisNoHandleException {
        System.out.println(new File("").getAbsolutePath());
        File file =new File(AnalysisTest.class.getClassLoader().getResource("x.galaxy").getFile());
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
        paramWrapper.put(123);
        paramWrapper = new HandlerScheduler().run(paramWrapper,map.get("namespace1.process1").getHandleList());
        System.out.println(paramWrapper.get(Integer.class));
    }
}
