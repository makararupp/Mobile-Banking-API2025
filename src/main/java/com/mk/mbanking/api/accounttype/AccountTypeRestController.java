package com.mk.mbanking.api.accounttype;

import com.mk.mbanking.base.BaseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;

    @GetMapping
    public BaseApi<?> findAllAccountType() {
        List<AccountTypeDto> accountTypeDto = accountTypeService.findAllAccountTypes();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("account types have been found")
                .data(accountTypeDto)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
}
