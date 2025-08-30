package com.mk.mbanking.api.user;

import com.mk.mbanking.base.BaseApi;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {
    @InsertProvider(type = UserProvider.class,
            method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(@Param("u") User user);

    @SelectProvider(type = UserProvider.class,
            method = "buildSelectByIdSql")
    @Results(id = "userResultMap", value = {
            @Result(column = "is_student", property = "isStudent"),
            @Result(column = "student_card_id", property = "studentCardId")
    })
    Optional<User> selectById(@Param("id") Long id);

    //TODO : check condition if user have in recode it's show message = true ,no hava= false
    @Select("""
            SELECT EXISTS(SELECT *
            FROM users
            WHERE id = #{id} AND is_deleted = FALSE)
            """)
    boolean exitsById(@Param("id")Long id);

    @UpdateProvider(type = UserProvider.class, method = "buildUpdateSql")
    void update(@Param("u") User user);

    @UpdateProvider(type = UserProvider.class, method = "updateIsDeletedSql")
    void updateIsDeleted(@Param("id")Long id, @Param("status")Boolean status);
}
