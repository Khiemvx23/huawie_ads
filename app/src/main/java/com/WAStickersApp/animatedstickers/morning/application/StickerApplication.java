/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.WAStickersApp.animatedstickers.morning.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.onesignal.OneSignal;

public class StickerApplication extends Application {
    private static final String ONESIGNAL_APP_ID = "2ee9cf4b-9382-4ab2-8a0b-8d631b4077d1";
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
