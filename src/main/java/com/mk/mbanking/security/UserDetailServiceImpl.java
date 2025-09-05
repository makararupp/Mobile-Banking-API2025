package com.mk.mbanking.security;

import com.mk.mbanking.api.auth.AuthMapper;
import com.mk.mbanking.api.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.info("loadUserByUserName: {}",username);
          User user = authMapper.selectByEmail(username).orElseThrow(()->
                  new UsernameNotFoundException("user not found..!"));
         log.info(user.getEmail());

        return null;
    }
}
