package com.ckmcknight.android.menufi;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Provides
    public Context getAppContext() {
        return app.getApplicationContext();
    }
}
