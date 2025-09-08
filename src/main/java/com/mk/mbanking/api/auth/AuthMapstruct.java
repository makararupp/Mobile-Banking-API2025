package com.mk.mbanking.api.auth;

import com.mk.mbanking.api.auth.web.RegisterDto;
import com.mk.mbanking.api.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapstruct {
    User toUser(RegisterDto dto);
}
