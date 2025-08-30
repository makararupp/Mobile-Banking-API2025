package com.mk.mbanking.api.user;

import com.mk.mbanking.api.user.web.SaveUserDto;
import com.mk.mbanking.api.user.web.UserDto;

public interface UserService {
    UserDto creat(SaveUserDto saveUserDto);
    UserDto findById(Long id);
    UserDto updateById(Long id, SaveUserDto saveUserDto);
    UserDto deleteById(Long id);
}
