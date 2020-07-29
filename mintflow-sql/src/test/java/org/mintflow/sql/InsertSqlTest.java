package org.mintflow.sql;

import org.junit.Test;
import org.mintflow.sql.base.SqlTestBase;
import org.mintflow.sql.dto.ItemTableDTO;

import java.util.ArrayList;
import java.util.List;

public class InsertSqlTest extends SqlTestBase {
    @Test
    public void insertTest(){
        List<ItemTableDTO> itemTableDTOS = new ArrayList<>();
        itemTableDTOS.add(new ItemTableDTO());
        itemTableDTOS.add(new ItemTableDTO());
        itemTableDTOS.add(new ItemTableDTO());
        itemTableDTOS.add(new ItemTableDTO());
        itemTableDTOS.add(new ItemTableDTO());
        Sql sql = Insert.sql().insert(itemTableDTOS).build();
        System.out.println();
    }
}
