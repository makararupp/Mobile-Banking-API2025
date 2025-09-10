package com.mk.mbanking.api.auth;

import com.mk.mbanking.api.auth.web.AuthDto;
import com.mk.mbanking.api.auth.web.LoginDto;
import com.mk.mbanking.api.auth.web.RegisterDto;
import com.mk.mbanking.api.user.User;
import com.mk.mbanking.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final AuthMapper authMapper;
    private final AuthMapstruct authMapstruct;
    //injection password encoder
    private final PasswordEncoder passwordEncoder;
    private final DaoAuthenticationProvider authProvider;

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        User user =  authMapstruct.toUser(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authMapper.register(user);
        //after registered
        for(Integer roleId : registerDto.roleIds()){
            authMapper.createUserRoles(user.getId(), roleId);
        }
    }

    @Override
    public AuthDto login(LoginDto loginDto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDto.email(),
                loginDto.password()
        );
        auth = authProvider.authenticate(auth);
        /*log.info("Auth :{}", auth.getName());
        log.info("Auth :{}", auth.getCredentials());
        log.info("Auth :{}", auth.getAuthorities().size());*/

        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();

        log.info("Auth :{}", customUserDetails.getUser().getEmail());
        //create following algorithm and RSA keys.
        String basicAuthString = auth.getName() + ":"+ auth.getCredentials();
        String basicAuthHeader = Base64.getEncoder().encodeToString(basicAuthString.getBytes());

        return new AuthDto(String.format("Basic %s",basicAuthHeader));
    }
}
