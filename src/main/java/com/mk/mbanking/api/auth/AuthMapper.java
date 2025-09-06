package com.mk.mbanking.api.auth;

import com.mk.mbanking.api.user.Role;
import com.mk.mbanking.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AuthMapper {
    @SelectProvider(type = AuthProvider.class, method = "buildSelectByEmailSql")
    @Results(id = "authResultMap", value = {
            @Result(property = "roles", column = "id",
                    many = @Many(select = "selectUserRoles")),
            @Result(property = "isDeleted", column = "is_deleted"),
            @Result(property = "isStudent", column = "is_student"),
            @Result(property = "studentCardId", column = "student_card_id")
    })
    Optional<User> selectByEmail(String email);

    @SelectProvider(type = AuthProvider.class, method = "buildSelectUserRolesSql")
    List<Role> selectUserRoles(Integer userId);

}
