package org.mintflow.test;

import org.mintflow.MintFlow;
import org.mintflow.MintFlowTemplate;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.handler.async.condition.AsyncConditionHandler1;
import org.mintflow.handler.async.condition.AsyncConditionHandler2;
import org.mintflow.handler.async.condition.AsyncConditionHandler3;
import org.mintflow.handler.async.condition.AsyncConditionHandler4;
import org.mintflow.handler.async.reorder.AsyncReorderHandler;
import org.mintflow.handler.async.sample.*;
import org.mintflow.handler.sync.condition.ConditionHandler1;
import org.mintflow.handler.sync.condition.ConditionHandler2;
import org.mintflow.handler.sync.condition.ConditionHandler3;
import org.mintflow.handler.sync.condition.ConditionHandler4;
import org.mintflow.handler.sync.reorder.ReorderHandler;
import org.mintflow.handler.sync.simple.*;
import org.mintflow.handler.util.MintFlowHandlerMapBuilder;
import org.mintflow.handler.util.MintFlowHandlerMapFinder;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncFnEngineSyncScheduler;
import org.mintflow.templateFunction.Function1;
import org.mintflow.test.asyncBaseTest.AsyncTempleteTest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.test.BaseTestUtil.*;
import static org.mintflow.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.mintflow.test.syncBaseTest.ConditionTest.NO_GO;
import static org.mintflow.test.syncBaseTest.ReorderTest.ADD_DATA;

public class PerformanceTest {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        for(int a=0;a<100;a++){
            new Thread(new AsyncNoUseTemplateThread()).start();
//            new Thread(new AsyncUseTemplateTest()).start();
//            new Thread(new SyncNoUseTemplateThread()).start();
//            new Thread(new SyncUseTemplateThread()).start();
        }
    }

    static class SyncUseTemplateThread implements Runnable{

        @Override
        public void run() {
            MintFlowHandlerMap dataMapper = MintFlowHandlerMapFinder.findHandlerDataMap(
                    "org.mintflow.handler"
            );
            MintFlow mintFlow = MintFlow.newBuilder(dataMapper).addFnMapper("base_sync_test/sync_complex_test.fn").build();
            MintFlowTemplate mintFlowTemplate = MintFlowTemplate.newBuilder().addInterface(mintFlow,"org.mintflow.templateFunction").build();
            Function1 function1 = mintFlowTemplate.getTemplateFunction(Function1.class);
            while(true) {
                ParamWrapper paramWrapper = function1.test(1, "item", NO_GO, CAN_GO, NO_GO, CAN_GO, false, false);

                assertEquals(13, (int) paramWrapper.getResult(Integer.class));
                StringBuilder ans = new StringBuilder("item" + ADD_DATA);
                int num = paramWrapper.getContextParam("random_number");
                while (num >= 0) {
                    ans.append(ADD_DATA);
                    num--;
                }
                String item = paramWrapper.getParam(String.class);
                assertEquals(ans.toString(), item);

                assertTrue(paramWrapper.getContextParam("show_start"));
                assertTrue(paramWrapper.getContextParam("show_end"));
                System.out.print(".");
                if(atomicInteger.incrementAndGet()==300){
                    System.out.println();
                    atomicInteger.set(0);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class SyncNoUseTemplateThread implements Runnable{

        @Override
        public void run() {
            MintFlowHandlerMapBuilder mapBuilder;
            mapBuilder = new MintFlowHandlerMapBuilder();

            mapBuilder.put("condition_handle_1",new ConditionHandler1("condition_handle_1"));
            mapBuilder.put("condition_handle_2",new ConditionHandler2("condition_handle_2"));
            mapBuilder.put("condition_handle_3",new ConditionHandler3("condition_handle_3"));
            mapBuilder.put("condition_handle_4",new ConditionHandler4("condition_handle_4"));


            mapBuilder.put("base_test_handle1",new BaseTestHandler1("base_test_handle1"));
            mapBuilder.put("base_test_handle2",new BaseTestHandler2("base_test_handle2"));
            mapBuilder.put("base_test_handle3",new BaseTestHandler3("base_test_handle3"));

            mapBuilder.put("show_start_handle",new ShowStartHandler("show_start_handle"));
            mapBuilder.put("show_end_handle",new ShowEndHandler("show_end_handle"));

            mapBuilder.put("reorder_handle",new ReorderHandler("reorder_handle"));
            mapBuilder.put("reorder_sample_handle",new ReorderSampleHandler("reorder_sample_handle"));
            while(true){
                String item = "test1";
                StringBuilder ans = new StringBuilder(item + ADD_DATA);
                ParamWrapper paramWrapper= new ParamWrapper();
                paramWrapper.setParam(1);
                paramWrapper.setParam(item);
                paramWrapper.setContextParam("condition_1",NO_GO);
                paramWrapper.setContextParam("condition_2",CAN_GO);
                paramWrapper.setContextParam("condition_3",NO_GO);
                paramWrapper.setContextParam("condition_4",CAN_GO);
                paramWrapper.setContextParam("show_start",false);
                paramWrapper.setContextParam("show_end",false);
                MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_complex_test.fn").build();
                paramWrapper = mintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
                assertEquals(13, (int) paramWrapper.getResult(Integer.class));

                int num = paramWrapper.getContextParam("random_number");
                while(num>=0){
                    ans.append(ADD_DATA);
                    num--;
                }
                item = paramWrapper.getParam(String.class);
                assertEquals(ans.toString(),item);

                assertTrue(paramWrapper.getContextParam("show_start"));
                assertTrue(paramWrapper.getContextParam("show_end"));
                System.out.print(".");
                if(atomicInteger.incrementAndGet()==300){
                    System.out.println();
                    atomicInteger.set(0);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class AsyncNoUseTemplateThread implements Runnable{

        @Override
        public void run() {
            /**
             * 初始化
             */
            MintFlowHandlerMapBuilder mapBuilder;
            mapBuilder = new MintFlowHandlerMapBuilder();

            mapBuilder.put("async_condition_handle_1",new AsyncConditionHandler1("async_condition_handle_1"));
            mapBuilder.put("async_condition_handle_2",new AsyncConditionHandler2("async_condition_handle_2"));
            mapBuilder.put("async_condition_handle_3",new AsyncConditionHandler3("async_condition_handle_3"));
            mapBuilder.put("async_condition_handle_4",new AsyncConditionHandler4("async_condition_handle_4"));

            mapBuilder.put("async_base_test_handle1",new AsyncBaseTestHandler1("async_base_test_handle1"));
            mapBuilder.put("async_base_test_handle2",new AsyncBaseTestHandler2("async_base_test_handle2"));
            mapBuilder.put("async_base_test_handle3",new AsyncBaseTestHandler3("async_base_test_handle3"));

            mapBuilder.put("async_show_start_handle",new AsyncShowStartHandler("async_show_start_handle"));
            mapBuilder.put("async_show_end_handle",new AsyncShowEndHandler("async_show_end_handle"));

            mapBuilder.put("async_reorder_handle",new AsyncReorderHandler("async_reorder_handle"));
            mapBuilder.put("async_reorder_sample_handle",new AsyncReorderSampleHandler("async_reorder_sample_handle"));
            MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_complex_test.fn").build();
            while(true){
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                String item = "test1";
                StringBuilder ans = new StringBuilder(item + ADD_DATA);
                ParamWrapper paramWrapper= new ParamWrapper();
                paramWrapper.setParam(1);
                paramWrapper.setParam(item);
                paramWrapper.setContextParam("condition_1",NO_GO);
                paramWrapper.setContextParam("condition_2",CAN_GO);
                paramWrapper.setContextParam("condition_3",NO_GO);
                paramWrapper.setContextParam("condition_4",CAN_GO);
                paramWrapper.setContextParam("show_start",false);
                paramWrapper.setContextParam("show_end",false);
                mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
                    assertEquals(13, (int) param.getResult(Integer.class));
                    int num = param.getContextParam("random_number");
                    while(num>=0){
                        ans.append(ADD_DATA);
                        num--;
                    }
                    String nowItem = param.getParam(String.class);
                    assertEquals(ans.toString(),nowItem);
                    assertTrue(param.getContextParam("show_start"));
                    assertTrue(param.getContextParam("show_end"));
                    atomicBoolean.set(true);
                });

                assertTrue(atomicBoolean.get());
                System.out.print(".");
                if(atomicInteger.incrementAndGet()==300){
                    System.out.println();
                    atomicInteger.set(0);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class AsyncUseTemplateTest implements Runnable{

        @Override
        public void run() {
            MintFlowHandlerMap dataMapper = MintFlowHandlerMapFinder.findHandlerDataMap(
                    "org.mintflow.handler"
            );
            MintFlow mintFlow = MintFlow.newBuilder(dataMapper).addFnMapper("base_async_test/async_complex_test.fn").build();
            MintFlowTemplate mintFlowTemplate = MintFlowTemplate.newBuilder().addInterface(mintFlow, "org.mintflow.templateFunction").build();
            Function1 function1 = mintFlowTemplate.getTemplateFunction(Function1.class);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            while(true) {
                function1.test(1, "item", NO_GO, CAN_GO, NO_GO, CAN_GO, false, false, param -> {
                    assertEquals(13, (int) param.getResult(Integer.class));
                    StringBuilder ans = new StringBuilder("item" + ADD_DATA);
                    int num = param.getContextParam("random_number");
                    while (num >= 0) {
                        ans.append(ADD_DATA);
                        num--;
                    }
                    String item = param.getParam(String.class);
                    assertEquals(ans.toString(), item);

                    assertTrue(param.getContextParam("show_start"));
                    assertTrue(param.getContextParam("show_end"));
                    atomicBoolean.set(true);
                });
                assertTrue(atomicBoolean.get());
                System.out.print(".");
                if(atomicInteger.incrementAndGet()==300){
                    System.out.println();
                    atomicInteger.set(0);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
