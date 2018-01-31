package com.ckmcknight.android.menufi;

import android.app.Application;

import com.ckmcknight.android.menufi.components.AccountComponent;
import com.ckmcknight.android.menufi.components.DaggerAccountComponent;
import com.ckmcknight.android.menufi.components.DaggerNetworkComponent;
import com.ckmcknight.android.menufi.components.NetworkComponent;

public class MenuFiApplication extends Application {
    AccountComponent accountComponent;
    NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        accountComponent = DaggerAccountComponent.create();
        networkComponent = DaggerNetworkComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public AccountComponent getAccountComponent() {
        return accountComponent;
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

}
