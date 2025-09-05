package com.mk.mbanking.api.auth;

import org.apache.ibatis.jdbc.SQL;

public class AuthProvider {
    private final String USERS = "users";

    public String buildSelectByEmailSql(){
        return new SQL(){{
            SELECT("*");
            FROM(USERS);
            WHERE("email = #{email}", "is_deleted = FALSE");
        }}.toString();
    }
}
