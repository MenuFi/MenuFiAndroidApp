package com.ckmcknight.android.menufi.model.AccountManagement;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

public class AccountModule {

    AccountManager providesAccountManager() {
        return new AccountManager();
    }
}
