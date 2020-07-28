package org.mintflow.sql;

import org.mintflow.reflection.MirrorObject;
import org.mintflow.sql.basis.SqlBase;
import org.mintflow.sql.type.SqlType;

import java.util.ArrayList;
import java.util.List;

public class Insert extends SqlBase {

    private Insert(SqlType sqlType) {
        super(sqlType);
    }

    public static Insert sql() {
        return new Insert(SqlType.INSERT);
    }

    public <T> Insert insert(String tableName, List<T> dateList) {

        if(dateList==null||dateList.size()==0){
            throw  new RuntimeException();
        }

        T defaultOne = dateList.get(0);

        List<Object> insertParamList = findParamsList(defaultOne);

        StringBuilder paramStr = createParamsArrays(insertParamList);

        this.sql.append(INSERT).append(SPLIT).append(INTO).append(SPLIT).append(TAG).append(tableName).append(TAG).append(SPLIT).append(LEFT_PARENTHESIS)
                .append(paramStr)
                .append(RIGHT_PARENTHESIS).append(SPLIT);
        StringBuilder dataArray = new StringBuilder();

        int defaultLength= insertParamList.size();

        boolean allStart = true;
        for (T t : dateList) {
            MirrorObject mirrorObject = MirrorObject.forObject(t);
            String[] nameList = mirrorObject.getGetterNames();
            if(allStart){
                dataArray.append(LEFT_PARENTHESIS);
                allStart = false;
            }else{
                dataArray.append(COMMA).append(LEFT_PARENTHESIS);
            }
            boolean start = true;
            int length = 0;
            for (String name : nameList) {
                Object item = mirrorObject.getValue(name);
                if (item != null) {
                    length++;
                    if(start){
                        dataArray.append(PLACEHOLDER);
                        start = false;
                    }else{
                        dataArray.append(COMMA).append(PLACEHOLDER);
                    }
                    paramList.add(item);
                }
            }
            if(defaultLength!=length){
                throw new RuntimeException();
            }
            dataArray.append(RIGHT_PARENTHESIS);
        }
        this.sql.append(VALUES).append(SPLIT).append(dataArray).append(SPLIT);
        return this;
    }

    private <T> List<Object> findParamsList(T defaultOne) {
        List<Object> ans = new ArrayList<>();
        MirrorObject mirrorObject = MirrorObject.forObject(defaultOne);
        String[] paramsName = mirrorObject.getGetterNames();
        for(String name : paramsName){
            Object value = mirrorObject.getValue(name);
            if(value!=null){
                ans.add(name);
            }
        }
        return ans;
    }


    public <T> Insert insert(String tableName, T params) {
        List<T> paramList = new ArrayList<>();
        paramList.add(params);
        return insert(tableName,paramList);
    }

    public <T> Insert insert(T params) {
        return insert(getTableName(params.getClass().getSimpleName()),params);
    }

}
