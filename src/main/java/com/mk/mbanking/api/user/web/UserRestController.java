package com.mk.mbanking.api.user.web;

import com.mk.mbanking.api.user.UserService;
import com.mk.mbanking.base.BaseApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public BaseApi<?> create(@Valid @RequestBody SaveUserDto saveUserDto){
       UserDto userDto = userService.creat(saveUserDto);
       return BaseApi.builder()
               .status(true)
               .code(HttpStatus.CREATED.value())
               .message("users account types has been created")
               .timestamp(LocalDateTime.now())
               .data(userDto)
               .build();
    }
    @GetMapping("/{id}")
    public BaseApi<?> findUserId(@PathVariable("id") Long id){
        UserDto dto = userService.findById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("user have been found!")
                .timestamp(LocalDateTime.now())
                .data(dto)
                .build();
    }
    @PutMapping("/{id}")
    public BaseApi<?> update(@PathVariable("id") Long id, @Valid @RequestBody SaveUserDto dto){
        UserDto userDto = userService.updateById(id,dto);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("users have been update")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }
    @DeleteMapping("/{id}")
    public BaseApi<?> deleteStatus(@PathVariable("id") Long id){
        UserDto dto = userService.deleteById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("users has been deleted")
                .timestamp(LocalDateTime.now())
                .data(dto)
                .build();
    }

}
