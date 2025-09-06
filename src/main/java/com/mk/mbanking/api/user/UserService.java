package com.mk.mbanking.api.user;

import com.github.pagehelper.PageInfo;
import com.mk.mbanking.api.user.web.SaveUserDto;
import com.mk.mbanking.api.user.web.UserDto;

import java.util.List;

public interface UserService {
    UserDto creat(SaveUserDto saveUserDto);
    UserDto findById(Integer id);
    UserDto updateById(Integer id, SaveUserDto saveUserDto);
    UserDto deleteById(Integer id);

    PageInfo<UserDto> findWithPaging(int pageNum, int pageSize);
   // List<UserDto> listAll();
}
