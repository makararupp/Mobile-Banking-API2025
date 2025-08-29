package com.mk.mbanking.api.user.web;

import com.mk.mbanking.api.user.web.SaveUserDto;
import com.mk.mbanking.base.BaseApi;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @PostMapping
    public BaseApi<?> create(@Valid @RequestBody SaveUserDto saveUserDto){
        log.info("SaveUserDto = {}",saveUserDto);
        return null;
    }
}
