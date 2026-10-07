/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wuc656.nowinandroid.core.ui

import android.os.Build

/**
 * 針對 Android 16 / 17 及其雙發布節奏 (Minor SDK 週期) 的相容性判斷工具。
 */
object NiaBuildCompat {

    /**
     * 是否為 Android 15 (Vanilla Ice Cream / API 35) 或以上。
     */
    val isAtLeastAndroid15: Boolean
        get() = Build.VERSION.SDK_INT >= 35

    /**
     * 是否為 Android 16 (Baklava / API 36) 或以上。
     */
    val isAtLeastAndroid16: Boolean
        get() = Build.VERSION.SDK_INT >= 36

    /**
     * 是否支援 Android 16 Minor 1 (API 36.1) 或以上。
     */
    val isAtLeastAndroid16Minor1: Boolean
        get() = isAtLeastAndroid16

    /**
     * 是否為 Android 17 (API 37) 或以上前瞻環境。
     */
    val isAtLeastAndroid17: Boolean
        get() = Build.VERSION.SDK_INT >= 37
}
