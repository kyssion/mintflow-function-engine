package org.mintflow.sql;

import org.junit.Assert;
import org.junit.Test;
import org.mintflow.sql.base.SqlTestBase;

import java.util.ArrayList;
import java.util.List;

public class SelectSqlTest extends SqlTestBase {

    @Test
    public void SampleSelectTest() {
        Sql sql = Select.sql().selectFrom("test_table", "name", "age").build();
        Assert.assertEquals(sql.getSql(), "select `name`,`age` from `test_table` ");



        Select select = Select.sql().selectFrom("table",Select.ALL);
        String name = null;
        if(name!=null){
            select.eq("name",name);
        }
        Integer age =123;
        if(age>0){
            select.eq("age",age);
        }
        Sql sql1 = select.build();
        String sqlStr = sql1.getSql();
        List<Object> params = sql1.getParamsList();
        System.out.println(sqlStr);
    }

    @Test
    public void conditionSelectTest() {
        List<Object> params = new ArrayList<>();
        params.add("jack");
        params.add(1);
        params.add(2);
        params.add(3);
        params.add(4);
        params.add(5);
        params.add(6);
        Sql sql = Select.sql().selectFrom("test_table", "name", "age", "num", "bookNum")
                .eq("name", params.get(0)).and()
                .between("age", params.get(1), params.get(2)).and()
                .gt("num", params.get(3)).and()
                .lt("num", params.get(4)).and()
                .gte("bookNum", params.get(5)).and()
                .ltq("bookNum", params.get(6))
                .build();
        Assert.assertEquals(sql.getSql(), "select `name`,`age`,`num`,`bookNum` from `test_table` where `name` = ? AND (`age` between ? AND ? ) AND `num` > ? AND `num` < ? AND `bookNum` >= ? AND `bookNum` <= ? ");
        Assert.assertArrayEquals(createArrays(params), createArrays(sql.getParamsList()));
    }

    @Test
    public void conditionSelectGroupBy() {
        List<Object> params = new ArrayList<>();
        params.add("jack");
        params.add(1);
        params.add(2);
        Select select = Select.sql();
        select.selectFrom("test_table", "name", "age", "num", "bookNum");
        select.eq("name", params.get(0));
        select.groupBy("name");
        select.orderByAsc("age");
        select.orderByDesc("num");
        select.limit((int) params.get(1), (int) params.get(2));

        Sql sql = select.build();
        Assert.assertEquals(sql.getSql(),"select `name`,`age`,`num`,`bookNum` from `test_table` where `name` = ? GROUP_BY `name` ORDER BY `age` ASC,`num` DESC LIMIT ?,? ");
        Assert.assertArrayEquals(createArrays(params),createArrays(sql.getParamsList()));
    }
}
