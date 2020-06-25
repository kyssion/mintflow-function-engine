package org.mintflow.handle.util;

import org.mintflow.handle.FnHandler;
import org.mintflow.handle.async.AsyncFnHandler;
import org.mintflow.handle.async.AsyncToolsFnHandler;
import org.mintflow.handle.sync.SyncFnHandler;
import org.mintflow.handle.sync.SyncToolsFnHandler;

public final class FnHandlerUtil {
    public static boolean checkHandlerCanUse(FnHandler fnHandler){
        return fnHandler instanceof AsyncToolsFnHandler || fnHandler instanceof AsyncFnHandler
                || fnHandler instanceof SyncFnHandler ||fnHandler instanceof SyncToolsFnHandler;
    }
}
