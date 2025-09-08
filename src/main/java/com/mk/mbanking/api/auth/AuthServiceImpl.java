package com.mk.mbanking.api.auth;

import com.mk.mbanking.api.auth.web.LoginDto;
import com.mk.mbanking.api.auth.web.RegisterDto;
import com.mk.mbanking.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthMapper authMapper;
    private final AuthMapstruct authMapstruct;
    //injection password encoder
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDto registerDto) {
        User user =  authMapstruct.toUser(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authMapper.register(user);
    }

    @Override
    public void login(LoginDto loginDto) {

    }
}
