package com.mk.mbanking.api.user;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private final String USERS = "users";

    public String buildSelectAllSql(){
        return new SQL(){{
            SELECT( "*");
            FROM(USERS);
            WHERE("is_deleted = FALSE");
        }}.toString();
    }
    public String buildSelectWithPagingSql() {
        return new SQL() {{
            SELECT("*");
            FROM(USERS);
            WHERE("is_deleted = FALSE");
            ORDER_BY("id DESC");
        }}.toString();
    }

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

    public String buildUpdateSql(){
        return new SQL(){{
            UPDATE(USERS);
            SET("name = #{u.name}");
            SET("gender = #{u.gender}");
            SET("is_student = #{u.isStudent}");
            SET("student_card_id = #{u.studentCardId}");
            WHERE("id = #{u.id}");
        }}.toString();
    }

    public String updateIsDeletedSql(){
        return new SQL(){{
            UPDATE(USERS);
            SET("is_deleted = #{status}");
            WHERE("id = #{id}");
        }}.toString();
    }

}
