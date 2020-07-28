package org.mintflow.sql;

import org.junit.Test;
import org.mintflow.sql.base.SqlTestBase;
import org.mintflow.sql.dto.ItemTableDTO;

public class InsertSqlTest extends SqlTestBase {
    @Test
    public void insertTest(){
        ItemTableDTO itemTableDTO = new ItemTableDTO();
        Sql sql = Insert.sql().insert(itemTableDTO).build();
        System.out.println();
    }
}
