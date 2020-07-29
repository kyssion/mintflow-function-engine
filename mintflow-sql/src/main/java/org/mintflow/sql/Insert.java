package org.mintflow.sql;

import org.mintflow.reflection.Reflector;
import org.mintflow.reflection.SampleMirrorObject;
import org.mintflow.reflection.agent.Agent;
import org.mintflow.sql.annotation.TableField;
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

    private <T> List<Object> findParamsList(T defaultOne) {
        List<Object> ans = new ArrayList<>();
        SampleMirrorObject sampleMirrorObject = SampleMirrorObject.forObject(defaultOne);
        Reflector reflector = sampleMirrorObject.getReflector();
        String[] getterNames = reflector.getGetablePropertyNames();
        for(String name : getterNames){
            Agent fieldAgent = reflector.getGetAgent(name);
            TableField tableField = fieldAgent.getAnnotation(TableField.class);
            Object item = sampleMirrorObject.getValue(name);
            if(item!=null){
                String tableFieldName = tableField.value();
                if("".equals(tableFieldName)){
                    tableFieldName = fieldAgent.getName();
                }
                ans.add(tableFieldName);
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
        return insert(getTableName(params),params);
    }

}
