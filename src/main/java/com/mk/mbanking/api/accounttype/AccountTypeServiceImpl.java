package com.mk.mbanking.api.accounttype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{

    private final AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountTypeDto> findAllAccountTypes() {
        List<AccountType> typeList = accountTypeMapper.select();
        List<AccountTypeDto> typeDtos = typeList.stream()
                .map(new Function<AccountType, AccountTypeDto>() {
                    @Override
                    public AccountTypeDto apply(AccountType accountType) {
                        return new AccountTypeDto(accountType.getName());
                    }
                }).toList();
        return typeDtos;
    }
}
