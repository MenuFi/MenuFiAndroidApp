package com.ckmcknight.android.menufi.components;

import com.ckmcknight.android.menufi.model.AccountManagement.AccountManager;

import javax.inject.Singleton;

import dagger.Component;

@Component
@Singleton
public interface AccountComponent {
    AccountManager accountManager();
}
