package com.mk.mbanking.api.user;

import com.mk.mbanking.api.user.web.SaveUserDto;
import com.mk.mbanking.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.desktop.PreferencesEvent;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserMapstruct userMapstruct;
    @Override
    public UserDto creat(SaveUserDto saveUserDto) {
        User user = userMapstruct.saveUserDtoToUser(saveUserDto);
        userMapper.insert(user);
        user = userMapper.selectById(user.getId()).orElseThrow(()->
                new RuntimeException("User is not found"));
        return userMapstruct.userToUserDto(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userMapper.selectById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with id =%d is not found",id)));
        return userMapstruct.userToUserDto(user);
    }

    @Override
    public UserDto updateById(Long id, SaveUserDto saveUserDto) {
        if(userMapper.exitsById(id)){
            //TODO: update user model
            User user = userMapstruct.saveUserDtoToUser(saveUserDto);
            user.setId(id);
            userMapper.update(user);
            return findById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("user with id = %d is not found in db",id));
    }

    @Override
    public UserDto deleteById(Long id) {
        if(userMapper.exitsById(id)){
            UserDto dto =findById(id);
            userMapper.updateIsDeleted(id, true);
            return dto;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("user with id=%d is    not found in db.",id));
    }
}
