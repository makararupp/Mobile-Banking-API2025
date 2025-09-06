package com.mk.mbanking.api.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mk.mbanking.api.user.web.SaveUserDto;
import com.mk.mbanking.api.user.web.UserDto;
import com.mk.mbanking.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserMapstruct userMapstruct;

    @Override
    public PageInfo<UserDto> findWithPaging(int pageNum, int pageSize) {
        PageInfo<User> userPageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(userMapper::select);

        return userMapstruct.userPageInfoToUserDtoPageInfo(userPageInfo);
    }

//    @Override
//    public List<UserDto> listAll() {
//        List<User> list = userMapper.findAll();
//        return userMapstruct.toDtoList(list);
//    }

    @Override
    public UserDto creat(SaveUserDto saveUserDto) {
        User user = userMapstruct.saveUserDtoToUser(saveUserDto);
        userMapper.insert(user);
        user = userMapper.selectById(user.getId()).orElseThrow(()->
                new RuntimeException("User is not found"));
        return userMapstruct.userToUserDto(user);
    }

    @Override
    public UserDto findById(Integer id) {
        User user = userMapper.selectById(id).orElseThrow(()->
                new ResourceNotFoundException("User","id",id));
        return userMapstruct.userToUserDto(user);
    }

    @Override
    public UserDto updateById(Integer id, SaveUserDto saveUserDto) {
        if(userMapper.existsById(id)){
            //TODO: update user model
            User user = userMapstruct.saveUserDtoToUser(saveUserDto);
            user.setId(id);
            userMapper.update(user);
            return findById(id);
        }
        throw new ResourceNotFoundException("User", "id",id);
    }

    @Override
    public UserDto deleteById(Integer id) {
        if(userMapper.existsById(id)){
            UserDto dto =findById(id);
            userMapper.updateIsDeleted(id, true);
            return dto;
        }
        throw new ResourceNotFoundException("User", "id",id);
    }
}
