package com.ckmcknight.android.menufi.dagger.modules;

import com.ckmcknight.android.menufi.model.accountvalidation.AccountValidator;
import com.ckmcknight.android.menufi.model.accountvalidation.MockAccountValidator;
import com.ckmcknight.android.menufi.model.accountvalidation.RemoteAccountValidator;
import com.ckmcknight.android.menufi.model.datafetchers.NetworkController;

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
