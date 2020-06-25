package org.mintflow.handler.util;

import org.mintflow.handler.FnHandler;
import org.mintflow.handler.async.AsyncFnHandler;
import org.mintflow.handler.async.AsyncToolsFnHandler;
import org.mintflow.handler.sync.SyncFnHandler;
import org.mintflow.handler.sync.SyncToolsFnHandler;

public final class FnHandlerUtil {
    public static boolean checkHandlerCanUse(FnHandler fnHandler){
        return fnHandler instanceof AsyncToolsFnHandler || fnHandler instanceof AsyncFnHandler
                || fnHandler instanceof SyncFnHandler ||fnHandler instanceof SyncToolsFnHandler;
    }
}
