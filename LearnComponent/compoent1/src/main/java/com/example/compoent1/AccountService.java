package com.example.compoent1;

import com.example.componentbase.service.IAccountService;

public class AccountService implements IAccountService {

    @Override
    public boolean isLogin() {
        return true;
    }

    @Override
    public String getAccountId() {
        return "小明";
    }
}
