package com.ckmcknight.android.menufi.components;

import com.ckmcknight.android.menufi.ApplicationModule;
import com.ckmcknight.android.menufi.model.datahandlers.RemoteMenuDataRetriever;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ApplicationModule.class)
@Singleton
public interface NetworkComponent {
    NetworkComponent controller();
    RemoteMenuDataRetriever dataRetriever();
}
