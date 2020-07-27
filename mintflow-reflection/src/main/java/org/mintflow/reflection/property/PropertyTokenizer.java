package org.mintflow.reflection.property;

import java.util.Iterator;

public class PropertyTokenizer implements Iterator<PropertyTokenizer> {
    private String name;
    private final String indexedName; //描述当前解析的节点
    private String index;// 如果是数组， 这里记录数组中的参数
    private final String children; // 子节点字符串

    /**
     * 如果参数是这样的xxx[ppp].zzz -> name = xxx , index = ppp , indexName = xxx[ppp], children = zzz
     * xxx.zzz.ppp -> name = xxx , index = null , indexName = xxx, children = zzz.ppp
     * @param fullName
     */
    public PropertyTokenizer(String fullName) {
        int delim = fullName.indexOf('.');
        if (delim > -1) {
            name = fullName.substring(0, delim);
            children = fullName.substring(delim + 1);
        } else {
            name = fullName;
            children = null;
        }
        indexedName = name;
        delim = name.indexOf('[');
        if (delim > -1) {
            index = name.substring(delim + 1, name.length() - 1);
            name = name.substring(0, delim);
        }
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }

    public String getIndexedName() {
        return indexedName;
    }

    public String getChildren() {
        return children;
    }

    @Override
    public boolean hasNext() {
        return children != null;
    }

    @Override
    public PropertyTokenizer next() {
        return new PropertyTokenizer(children);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
    }
}
