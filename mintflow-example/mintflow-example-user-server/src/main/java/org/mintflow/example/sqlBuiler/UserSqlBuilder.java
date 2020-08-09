package org.mintflow.example.sqlBuiler;

import org.mintflow.example.bean.User;
import org.mintflow.sql.Select;
import org.mintflow.sql.Sql;

public class UserSqlBuilder {
    public static Sql selectUser(User user){
        return Select.sql().SelectAll("user").eq("user_name",user.getName()).and().eq("user_age",user.getUser()).build();
    }
}
