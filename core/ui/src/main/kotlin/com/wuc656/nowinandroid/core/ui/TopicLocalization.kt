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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

object TopicLocalization {
    val nameMap = mapOf(
        "Android Studio" to "Android Studio",
        "Compose" to "Jetpack Compose",
        "Kotlin" to "Kotlin 程式語言",
        "Performance" to "效能優化",
        "Architecture" to "架構設計",
        "Testing" to "測試與品質",
        "UI & UX" to "使用者介面與體驗",
        "Gradle" to "Gradle 建置系統",
        "Accessibility" to "無障礙輔助",
        "Modularization" to "模組化開發",
        "Data Storage" to "資料儲存",
        "Dependency Injection" to "依賴注入 (DI)",
        "Security" to "資訊安全",
        "WorkManager" to "背景任務 (WorkManager)",
        "Camera & Media" to "相機與媒體",
    )

    val descriptionMap = mapOf(
        "The latest news on Android Studio and the Android toolchain." to "關於 Android Studio 與 Android 工具鏈的最新消息。",
        "Keep up with the latest Jetpack Compose news, libraries and more." to "追蹤 Jetpack Compose 的最新消息、程式庫與更多內容。",
        "The latest news for Kotlin, the first-class language for Android development." to "Android 開發首選語言 Kotlin 的最新消息。",
        "The latest news on Android performance, power, and efficiency." to "關於 Android 效能、功耗與效率的最新消息。",
        "The latest news on Android app architecture and Jetpack libraries." to "關於 Android 應用程式架構與 Jetpack 程式庫的最新消息。",
        "The latest news on Android testing and quality." to "關於 Android 測試與品質的最新消息。",
        "The latest news on Android UI, UX, and design." to "關於 Android 使用者介面、體驗與設計的最新消息。",
        "The latest news on the Android Gradle plugin and build system." to "關於 Android Gradle 外掛程式與建置系統的最新消息。",
        "The latest news on Android accessibility and inclusive design." to "關於 Android 無障礙功能與包容性設計的最新消息。",
        "The latest news on Android app modularization and multi-module builds." to "關於 Android 應用程式模組化與多模組建置的最新消息。",
        "The latest news on Android data storage and persistence." to "關於 Android 資料儲存與持續性的最新消息。",
        "The latest news on dependency injection in Android apps." to "關於 Android 應用程式中依賴注入的最新消息。",
        "The latest news on Android security and privacy." to "關於 Android 資訊安全與隱私的最新消息。",
        "The latest news on background work and WorkManager." to "關於背景任務與 WorkManager 的最新消息。",
        "The latest news on Android camera and media APIs." to "關於 Android 相機與媒體 API 的最新消息。",
    )
}

@Composable
fun rememberTranslatedName(name: String): String {
    return remember(name) { TopicLocalization.nameMap[name] ?: name }
}

@Composable
fun rememberTranslatedDescription(description: String): String {
    return remember(description) { TopicLocalization.descriptionMap[description] ?: description }
}
