package com.mk.mbanking.api.auth;

import com.mk.mbanking.api.auth.web.AuthDto;
import com.mk.mbanking.api.auth.web.LoginDto;
import com.mk.mbanking.api.auth.web.RegisterDto;


public interface AuthService {
    void register(RegisterDto registerDto);
    AuthDto login(LoginDto loginDto);
}
