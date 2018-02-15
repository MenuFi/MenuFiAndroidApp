package com.ckmcknight.android.menufi;

import android.app.Application;

import com.ckmcknight.android.menufi.dagger.components.MenuFiComponent;
import com.ckmcknight.android.menufi.dagger.modules.ApplicationModule;
import com.ckmcknight.android.menufi.dagger.components.DaggerMenuFiComponent;

public class MenuFiApplication extends Application {
    MenuFiComponent menuFiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationModule applicationModule = new ApplicationModule(this);
        menuFiComponent = DaggerMenuFiComponent.builder().applicationModule(applicationModule).build();
        startUp();
    }

    public MenuFiComponent getMenuFiComponent() {
        return menuFiComponent;
    }

    private void startUp() {
        menuFiComponent.getDietaryPreferenceStore().syncDietaryPreferences();
        menuFiComponent.getUserSharedPreferences().restablishCurrentSession();
    }

}
