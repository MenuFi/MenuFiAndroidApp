package com.ckmcknight.android.menufi.dagger.components;

import android.content.Context;

import com.ckmcknight.android.menufi.dagger.modules.AccountModule;
import com.ckmcknight.android.menufi.dagger.modules.ApplicationModule;
import com.ckmcknight.android.menufi.model.AccountManagement.AccountValidator;
import com.ckmcknight.android.menufi.model.DataStores.UserSharedPreferences;
import com.ckmcknight.android.menufi.model.datahandlers.RemoteMenuDataRetriever;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class, AccountModule.class})
@Singleton
public interface MenuFiComponent {
    MenuFiComponent controller();
    RemoteMenuDataRetriever dataRetriever();
    AccountValidator accountValidator();
    Context getApplicationContext();
    UserSharedPreferences getUserSharedPreferences();
}
