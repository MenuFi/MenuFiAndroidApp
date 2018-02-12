package com.ckmcknight.android.menufi.dagger.modules;

import com.ckmcknight.android.menufi.model.AccountManagement.AccountValidator;
import com.ckmcknight.android.menufi.model.AccountManagement.RemoteAccountValidator;
import com.ckmcknight.android.menufi.model.datahandlers.NetworkController;

import dagger.Provides;

public class AccountModule {

    @Provides
    public AccountValidator getAccountValidator(NetworkController controller) {
        return new RemoteAccountValidator(controller);
    }
}
