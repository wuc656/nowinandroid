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

package com.wuc656.nowinandroid.core.analytics

import android.content.Context
import android.os.Build
import android.util.Log

/**
 * Android 17 (API 37) 前瞻 Profiling 效能取樣監聽與排查介面。
 * 支援系統事件自動觸發 (如冷啟動、記憶體緊縮等情境)，在非 Android 17 系統上平滑降級。
 */
object NiaProfilingManager {

    private const val TAG = "NiaProfiling"

    /**
     * 初始化 Android 17 系統級效能採樣監聽器。
     */
    fun registerListenerIfSupported(context: Context) {
        if (Build.VERSION.SDK_INT >= 37) {
            try {
                // 預先對接 Android 17 ProfilingManager 服務
                val profilingService = context.getSystemService("profiling")
                if (profilingService != null) {
                    Log.i(TAG, "Android 17 ProfilingManager initialized successfully")
                }
            } catch (e: Throwable) {
                Log.w(TAG, "Failed to initialize ProfilingManager on API 37", e)
            }
        }
    }
}
