// Copyright 2020 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.androidbrowserhelper.playbilling.digitalgoods;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;

import com.android.billingclient.api.SkuDetails;
import com.google.androidbrowserhelper.playbilling.provider.BillingWrapper;
import com.google.androidbrowserhelper.playbilling.provider.BillingWrapperFactory;
import com.google.androidbrowserhelper.trusted.ExtraCommandHandler;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.trusted.TrustedWebActivityCallbackRemote;

public class DigitalGoodsRequestHandler implements ExtraCommandHandler {
    private final BillingWrapper mWrapper;
    private final BillingWrapper.Listener mListener = new BillingWrapper.Listener() {
        @Override
        public void onConnected() {
            // TODO: Sort out connection lifecycle.
        }

        @Override
        public void onDisconnected() {
            // TODO: Sort out connection lifecycle.
        }

        @Override
        public void onPurchaseFlowComplete(int result) { }
    };

    public DigitalGoodsRequestHandler(Context context) {
        mWrapper = BillingWrapperFactory.get(context, mListener);
    }

    @NonNull
    @Override
    public Bundle handleExtraCommand(Context context, String commandName, Bundle args,
            @Nullable TrustedWebActivityCallbackRemote callback) {
        DigitalGoodsCallback wrappedCallback = (callbackName, callbackArgs) -> {
            try {
                callback.runExtraCallback(callbackName, callbackArgs);
            } catch (RemoteException e) {
                // The remote app crashed/got shut down, not much we can do.
            }
        };

        boolean success = handle(commandName, args, wrappedCallback);

        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_COMMAND_SUCCESS, success);
        return bundle;
    }

    public boolean handle(@NonNull String commandName, @NonNull Bundle args,
            @Nullable DigitalGoodsCallback callback) {
        switch (commandName) {
            case GetDetailsCall.COMMAND_NAME:
                GetDetailsCall getDetailsCall = GetDetailsCall.create(args, callback);
                if (getDetailsCall == null) break;
                getDetailsCall.call(mWrapper);
                return true;
        }
        return false;
    }
}
