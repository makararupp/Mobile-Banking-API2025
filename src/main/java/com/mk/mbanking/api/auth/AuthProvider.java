package com.mk.mbanking.api.auth;

import org.apache.ibatis.jdbc.SQL;

public class AuthProvider {

    public String buildSelectUserRolesSql(){
        return new SQL() {{
            SELECT("r.id, r.name");
            FROM("roles AS r");
            INNER_JOIN("users_roles AS ur ON ur.role_id = r.id");
            WHERE("ur.user_id = #{userId}");
        }}.toString();
    }

    public String buildSelectByEmailSql(){
        return new SQL() {{
            SELECT("*");
            FROM("users");
            WHERE("email = #{email}", "is_deleted = FALSE");
        }}.toString();
    }
}
