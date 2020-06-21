package org.melkweg.handle.util;

import org.melkweg.handle.FnHandler;
import org.melkweg.handle.async.AsyncFnHandler;
import org.melkweg.handle.async.AsyncToolsFnHandler;
import org.melkweg.handle.sync.SyncFnHandler;
import org.melkweg.handle.sync.SyncToolsFnHandler;

public final class FnHandlerUtil {
    public static boolean checkHandleCanUse(FnHandler fnHandler){
        return fnHandler instanceof AsyncToolsFnHandler || fnHandler instanceof AsyncFnHandler
                || fnHandler instanceof SyncFnHandler ||fnHandler instanceof SyncToolsFnHandler;
    }
}
