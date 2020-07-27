package org.mintflow.sql;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SelectSqlTest {

    public Object[] createArrays(List<Object> list){
        Object[] objects = new Object[list.size()];
        for(int a=0;a<objects.length;a++){
            objects[a] = list.get(a);
        }
        return objects;
    }

    @Test
    public void SampleSelectTest(){
        Sql sql= Select.sql().selectFrom("test_table","name","age").build();
        Assert.assertEquals(sql.getSql(),"select `name`,`age` from `test_table`");
    }

    @Test
    public void ConditionSelectTest(){
        List<Object> params = new ArrayList<>();
        params.add("jack");
        params.add(1);
        params.add(2);
        params.add(3);
        params.add(4);
        params.add(5);
        params.add(6);

        Sql sql= Select.sql().selectFrom("test_table","name","age","num","bookNum")
                .eq("name",params.get(0)).and()
                .between("age",params.get(1),params.get(2)).and()
                .gt("num",params.get(3)).and()
                .lt("num",params.get(4)).and()
                .gte("bookNum",params.get(5)).and()
                .ltq("bookNum",params.get(6))
                .build();
        Assert.assertEquals(sql.getSql(),"select `name`,`age`,`num`,`bookNum` from `test_table` where `name` = ? AND (`age` between ? AND ? ) AND `num` > ? AND `num` < ? AND `bookNum` >= ? AND `bookNum` <= ? ");
        Assert.assertArrayEquals(createArrays(params),createArrays(sql.getParamsList()));
    }
}
