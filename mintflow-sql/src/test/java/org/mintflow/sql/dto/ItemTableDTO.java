package org.mintflow.sql.dto;

import org.mintflow.sql.annotation.TableField;
import org.mintflow.sql.annotation.TableName;

@TableName(value = "item_table")
public class ItemTableDTO {

    @TableField(value = "name")
    private String name = "name";

    @TableField(value = "age")
    private String age = "age";

    @TableField(value = "num")
    private String num = "num";

    @TableField(value = "num1")
    private String num1 = "num1";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }
}
