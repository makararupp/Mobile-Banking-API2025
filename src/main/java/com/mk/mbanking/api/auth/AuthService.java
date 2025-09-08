package com.mk.mbanking.api.auth;

import com.mk.mbanking.api.auth.web.LoginDto;
import com.mk.mbanking.api.auth.web.RegisterDto;


public interface AuthService {
    void register(RegisterDto registerDto);
    void login(LoginDto loginDto);
}
