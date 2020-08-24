package org.mintflow.util;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isNullOrEmpty(Collection<?> collection){
        return collection==null||collection.size()==0;
    }
}
