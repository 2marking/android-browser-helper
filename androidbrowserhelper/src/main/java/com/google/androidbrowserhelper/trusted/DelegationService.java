// Copyright 2019 Google Inc. All Rights Reserved.
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

package com.google.androidbrowserhelper.trusted;

import androidx.annotation.NonNull;
import androidx.browser.trusted.TokenStore;

/**
 * An extension of {@link androidx.browser.trusted.TrustedWebActivityService} that implements
 * {@link androidx.browser.trusted.TrustedWebActivityService#getTokenStore()} using a
 * {@link SharedPreferencesTokenStore}.
 */
public class DelegationService extends androidx.browser.trusted.TrustedWebActivityService {

    @NonNull
    @Override
    public TokenStore getTokenStore() {
        return new SharedPreferencesTokenStore(this);
    }
}
