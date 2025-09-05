package com.mk.mbanking.api.auth;

import com.mk.mbanking.api.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface AuthMapper {
    @SelectProvider(type = AuthProvider.class, method = "buildSelectByEmailSql")
    Optional<User> selectByEmail(String email);

}
