package org.mintflow.sql;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mintflow.sql.base.SqlTestBase;
import org.mintflow.sql.dto.ItemTableDTO;

import java.util.ArrayList;
import java.util.List;

public class UpdateSqlTest extends SqlTestBase {

    @Test
    @Ignore
    public void SampleSelectTest() {
        List<Object> defaultValue = new ArrayList<>();
        defaultValue.add("name");
        defaultValue.add("age");
        defaultValue.add("num");
        defaultValue.add("num1");
        Sql sql = Update.sql().update("test_table", new ItemTableDTO()).build();
        Assert.assertEquals(sql.getSql(), "update `test_table` SET `num`=?,`name`=?,`num1`=?,`age`=? ");
        Assert.assertArrayEquals(createArrays(defaultValue), createArrays(sql.getParamsList()));
    }

    @Test
    @Ignore
    public void SampleSelectTestNull() {
        List<Object> defaultValue = new ArrayList<>();
        defaultValue.add("name");
        defaultValue.add("num");
        defaultValue.add("num1");
        ItemTableDTO itemTableDTO = new ItemTableDTO();
        itemTableDTO.setAge(null);
        Sql sql = Update.sql().update("test_table", itemTableDTO).build();
        Assert.assertEquals(sql.getSql(), "update `test_table` SET `num`=?,`num1`=?,`name`=? ");
        Assert.assertArrayEquals(createArrays(defaultValue), createArrays(sql.getParamsList()));
    }

    @Test
    @Ignore
    public void conditionSelectTest() {
        List<Object> params = new ArrayList<>();
        params.add("jack");
        params.add(1);
        params.add(2);
        params.add(3);
        params.add(4);
        params.add(5);
        params.add(6);
        Sql sql = Update.sql().update("test_table", new ItemTableDTO())
                .eq("name", params.get(0)).and()
                .between("age", params.get(1), params.get(2)).and()
                .gt("num", params.get(3)).and()
                .lt("num", params.get(4)).and()
                .gte("bookNum", params.get(5)).and()
                .ltq("bookNum", params.get(6))
                .build();
        Assert.assertEquals(sql.getSql(), "update `test_table` SET `num`=?,`num1`=?,`name`=?,`age`=? where `name` = ? AND (`age` between ? AND ? ) AND `num` > ? AND `num` < ? AND `bookNum` >= ? AND `bookNum` <= ? ");
        List<Object> defaultValue = new ArrayList<>();
        defaultValue.add("name");
        defaultValue.add("age");
        defaultValue.add("num");
        defaultValue.add("num1");
        defaultValue.addAll(params);
        Assert.assertArrayEquals(createArrays(defaultValue), createArrays(sql.getParamsList()));
    }

    @Test
    @Ignore
    public void conditionSelectGroupBy() {
        List<Object> params = new ArrayList<>();
        params.add("jack");
        params.add(1);
        params.add(2);
        Update update = Update.sql();
        update.update("test_table", new ItemTableDTO());
        update.eq("name", params.get(0));
        update.groupBy("name");
        update.orderByAsc("age");
        update.orderByDesc("num");
        update.limit((int) params.get(1), (int) params.get(2));

        Sql sql = update.build();
        Assert.assertEquals(sql.getSql(),"update `test_table` SET `num`=?,`num1`=?,`name`=?,`age`=? where `name` = ? ");
        List<Object> defaultValue = new ArrayList<>();
        defaultValue.add("name");
        defaultValue.add("age");
        defaultValue.add("num");
        defaultValue.add("num1");
        defaultValue.addAll(params);
        Assert.assertArrayEquals(createArrays(defaultValue),createArrays(sql.getParamsList()));
    }
}
