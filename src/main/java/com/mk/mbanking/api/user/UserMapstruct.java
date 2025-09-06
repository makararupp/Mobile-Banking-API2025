package com.mk.mbanking.api.user;

import com.github.pagehelper.PageInfo;
import com.mk.mbanking.api.user.web.SaveUserDto;
import com.mk.mbanking.api.user.web.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapstruct {
     User saveUserDtoToUser(SaveUserDto dto);
     UserDto userToUserDto(User user);
     PageInfo<UserDto> userPageInfoToUserDtoPageInfo(PageInfo<User> model);

}
