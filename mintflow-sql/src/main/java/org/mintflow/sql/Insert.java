package org.mintflow.sql;

import org.mintflow.reflection.Reflector;
import org.mintflow.reflection.SampleMirrorObject;
import org.mintflow.sql.basis.SqlBase;
import org.mintflow.sql.type.SqlType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Insert extends SqlBase {

    private Insert(SqlType sqlType) {
        super(sqlType);
    }

    public static Insert sql() {
        return new Insert(SqlType.INSERT);
    }

    public Insert insert(String tableName,String[] paramsNames,Object[] params){
        if(paramsNames.length!=params.length){
            throw new RuntimeException();
        }
        StringBuilder paramStr = createParamsArrays((Object[]) paramsNames);
        this.sql.append(INSERT).append(SPLIT).append(INTO).append(SPLIT).append(TAG).append(tableName).append(TAG).append(SPLIT).append(LEFT_PARENTHESIS)
                .append(paramStr)
                .append(RIGHT_PARENTHESIS).append(SPLIT);
        StringBuilder dataArray = new StringBuilder(LEFT_PARENTHESIS);
        boolean isStart = true;
        for(Object item : params){
            if(item==null){
                throw new RuntimeException();
            }
            if(isStart){
                dataArray.append(PLACEHOLDER);
                isStart=false;
            }else{
                dataArray.append(COMMA).append(PLACEHOLDER);
            }
            this.paramList.add(item);
        }
        dataArray.append(RIGHT_PARENTHESIS).append(SPLIT);
        this.sql.append(dataArray);
        return this;
    }

    public <T> Insert insert(List<T> dateList) {
        if(dateList==null||dateList.size()==0){
            throw  new RuntimeException();
        }
        T defaultOne = dateList.get(0);
        String tableName = getTableName(defaultOne);
        List<String> insertParamList = findParamsList(defaultOne);

        if(insertParamList.size()==0){
            throw new RuntimeException();
        }

        StringBuilder paramStr = createParamsArrays(insertParamList);
        this.sql.append(INSERT).append(SPLIT).append(INTO).append(SPLIT).append(TAG).append(tableName).append(TAG).append(SPLIT).append(LEFT_PARENTHESIS)
                .append(paramStr)
                .append(RIGHT_PARENTHESIS).append(SPLIT);
        StringBuilder dataArray = new StringBuilder();
        int defaultLength= insertParamList.size();
        boolean allStart = true;
        for (T t : dateList) {
            SampleMirrorObject mirrorObject = SampleMirrorObject.forObject(t);
            Reflector reflector = mirrorObject.getReflector();
            String[] nameList = reflector.getGetablePropertyNames();
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
    public static List<Object> createParams(Object...item){
        return new ArrayList<>(Arrays.asList(item));
    }
}
