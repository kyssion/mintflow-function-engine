package org.mintflow.sql;

import org.junit.Assert;
import org.junit.Test;
import org.mintflow.sql.base.SqlTestBase;

import java.util.ArrayList;
import java.util.List;

public class DeleteSqlTest extends SqlTestBase {
    @Test
    public void SampleSelectTest() {
        List<Object> params = new ArrayList<>();
        params.add("jack");
        params.add(1);
        params.add(2);
        params.add(3);
        params.add(4);
        params.add(5);
        params.add(6);
        Sql sql = Delete.sql().delete("test_table").eq("name", params.get(0)).and()
                .between("age", params.get(1), params.get(2)).and()
                .gt("num", params.get(3)).and()
                .lt("num", params.get(4)).and()
                .gte("bookNum", params.get(5)).and()
                .ltq("bookNum", params.get(6))
                .build();
        Assert.assertEquals(sql.getSql(), "delete from`test_table` where `name` = ? AND (`age` between ? AND ? ) AND `num` > ? AND `num` < ? AND `bookNum` >= ? AND `bookNum` <= ? ");
        Assert.assertArrayEquals(createArrays(params), createArrays(sql.getParamsList()));
    }


}
