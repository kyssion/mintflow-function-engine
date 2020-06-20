package org.melkweg.handle.util;

import org.melkweg.handle.FnHandler;
import org.melkweg.handle.async.AsyncFnHandle;
import org.melkweg.handle.async.AsyncToolsFnHandle;
import org.melkweg.handle.sync.SyncFnHandle;
import org.melkweg.handle.sync.SyncToolsFnHandle;

public final class FnHandlerUtil {
    public static boolean checkHandleCanUse(FnHandler fnHandler){
        return fnHandler instanceof AsyncToolsFnHandle || fnHandler instanceof AsyncFnHandle
                || fnHandler instanceof SyncFnHandle ||fnHandler instanceof SyncToolsFnHandle;
    }
}
