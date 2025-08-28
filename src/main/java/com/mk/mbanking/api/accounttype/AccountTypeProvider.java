package com.mk.mbanking.api.accounttype;

import org.apache.ibatis.jdbc.SQL;
public class AccountTypeProvider {

    public String buildSelectAccountType(){
        return new SQL(){{
            SELECT("*");
            FROM("account_types");
        }}.toString();
    }
}
