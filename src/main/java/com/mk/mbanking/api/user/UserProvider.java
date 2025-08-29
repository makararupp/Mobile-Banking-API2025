package com.mk.mbanking.api.user;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    public String buildSelectByIdSql(){
        return new SQL() {{
            SELECT("*");
            FROM("users");
            WHERE("id = #{id}");
            AND();
            WHERE("is_deleted = FALSE");
        }}.toString();
    }

    public String buildInsertSql(){
        return new SQL() {{
            INSERT_INTO("users");
            VALUES("name", "#{u.name}");
            VALUES("gender", "#{u.gender}");
            VALUES("is_deleted", "FALSE");
            VALUES("is_student", "#{u.isStudent}");
            VALUES("student_card_id", "#{u.studentCardId}");
        }}.toString();
    }
}
