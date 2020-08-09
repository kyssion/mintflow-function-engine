package org.mintflow.util;

public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * isNullOrEmpty(a)||isNullOrEmpty(b)||isNullOrEmpty(c)
     * @param strings
     * @return
     */
    public static boolean isNullOrEmpty(String...strings) {
        for(String s:strings){
            if(isNullOrEmpty(s)){
                return true;
            }
        }
        return false;
    }

    public static String dealWithUrl(String parentUrl) {
        return parentUrl.startsWith("/")?parentUrl:"/"+parentUrl;
    }
}
