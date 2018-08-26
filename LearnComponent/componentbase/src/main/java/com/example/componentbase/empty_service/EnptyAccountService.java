package com.example.componentbase.empty_service;

import com.example.componentbase.service.IAccountService;

public class EnptyAccountService implements IAccountService {

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public String getAccountId() {
        return null;
    }
}
