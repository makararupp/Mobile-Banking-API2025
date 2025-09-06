package com.mk.mbanking;

import com.mk.mbanking.api.auth.AuthMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MobileBankingApiMainTests {

    @Autowired
    private AuthMapper authMapper;
    @Test
    void contextLoads() {
    }

    @Test
    void testSelectUserByEmail(){
        System.out.println(authMapper.selectByEmail("makarasam68@gmail.com"));
    }

    @Test
    void testSelectUserRoles(Integer id){
        System.out.println(authMapper.selectUserRoles(id));
    }

}
