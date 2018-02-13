package com.ckmcknight.android.menufi.dagger.modules;

import com.ckmcknight.android.menufi.model.AccountManagement.AccountValidator;
import com.ckmcknight.android.menufi.model.AccountManagement.MockAccountValidator;
import com.ckmcknight.android.menufi.model.AccountManagement.RemoteAccountValidator;
import com.ckmcknight.android.menufi.model.datahandlers.NetworkController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AccountModule {
    private boolean mock = true;

    @Provides
    @Singleton
    AccountValidator getAccountValidator(NetworkController controller) {
        if (mock) {
            return new MockAccountValidator();
        } else {
            return new RemoteAccountValidator(controller);
        }
    }
}
