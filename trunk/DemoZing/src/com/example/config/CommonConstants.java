/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.config;

/**
 *
 * A set of constants used by all of the components in this application. To use these constants
 * the components implement the interface.
 */

public final class CommonConstants {

    public CommonConstants() {

        // don't allow the class to be instantiated
    }

    
    public static final String ACTION_SNOOZE = "com.example.android.pingme.ACTION_SNOOZE";
    public static final String ACTION_DISMISS = "com.example.android.pingme.ACTION_DISMISS";
    public static final String ACTION_PING = "com.example.android.pingme.ACTION_PING";
    public static final String EXTRA_MESSAGE= "com.example.demozing.service.EXTRA_MESSAGE";
    public static final String EXTRA_TIMER = "com.example.android.pingme.EXTRA_TIMER";
    public static final int NOTIFICATION_ID = 001;
    public static final int UPLOAD_NOTIFICATION_ID = 002;
    public static final String EXTRA_PATH_FILE = "com.example.demozing.service.downloadservice.EXTRA_PATH_FILE";
    public static final String EXTRA_TITLE = "com.example.demozing.service.downloadservice.EXTRA_TITLE";
    public static final String EXTRA_PATH_THUMS = "com.example.demozing.service.downloadservice.EXTRA_PATH_THUMS";
    public static final String EXTRA_DURATION = "com.example.demozing.service.downloadservice.DURATION";
}
