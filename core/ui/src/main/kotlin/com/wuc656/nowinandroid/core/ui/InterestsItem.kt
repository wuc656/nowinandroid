/*
 * Copyright 2024 The Android Open Source Project
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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wuc656.nowinandroid.core.designsystem.component.DynamicAsyncImage
import com.wuc656.nowinandroid.core.designsystem.component.NiaIconToggleButton
import com.wuc656.nowinandroid.core.designsystem.icon.NiaIcons
import com.wuc656.nowinandroid.core.designsystem.theme.NiaTheme
import com.wuc656.nowinandroid.core.ui.R.string

private val topicTranslationMap = mapOf(
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

private val descriptionTranslationMap = mapOf(
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

@Composable
fun InterestsItem(
    name: String,
    following: Boolean,
    topicImageUrl: String,
    onClick: () -> Unit,
    onFollowButtonClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    description: String = "",
    isSelected: Boolean = false,
) {
    ListItem(
        leadingContent = {
            InterestsIcon(topicImageUrl, iconModifier.size(48.dp))
        },
        headlineContent = {
            val translatedName = topicTranslationMap[name] ?: name
            Text(text = translatedName)
        },
        supportingContent = {
            val translatedDescription = descriptionTranslationMap[description] ?: description
            Text(text = translatedDescription)
        },
        trailingContent = {
            NiaIconToggleButton(
                checked = following,
                onCheckedChange = onFollowButtonClick,
                icon = {
                    Icon(
                        imageVector = NiaIcons.Add,
                        contentDescription = stringResource(
                            id = string.core_ui_interests_card_follow_button_content_desc,
                        ),
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = NiaIcons.Check,
                        contentDescription = stringResource(
                            id = string.core_ui_interests_card_unfollow_button_content_desc,
                        ),
                    )
                },
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                Color.Transparent
            },
        ),
        modifier = modifier
            .semantics(mergeDescendants = true) {
                selected = isSelected
            }
            .clickable(enabled = true, onClick = onClick),
    )
}

@Composable
private fun InterestsIcon(topicImageUrl: String, modifier: Modifier = Modifier) {
    if (topicImageUrl.isEmpty()) {
        Icon(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(4.dp),
            imageVector = NiaIcons.Person,
            // decorative image
            contentDescription = null,
        )
    } else {
        DynamicAsyncImage(
            imageUrl = topicImageUrl,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun InterestsCardPreview() {
    NiaTheme {
        Surface {
            InterestsItem(
                name = "Compose",
                description = "Description",
                following = false,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardLongNamePreview() {
    NiaTheme {
        Surface {
            InterestsItem(
                name = "This is a very very very very long name",
                description = "Description",
                following = true,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardLongDescriptionPreview() {
    NiaTheme {
        Surface {
            InterestsItem(
                name = "Compose",
                description = "This is a very very very very very very very " +
                    "very very very long description",
                following = false,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardWithEmptyDescriptionPreview() {
    NiaTheme {
        Surface {
            InterestsItem(
                name = "Compose",
                description = "",
                following = true,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardSelectedPreview() {
    NiaTheme {
        Surface {
            InterestsItem(
                name = "Compose",
                description = "",
                following = true,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
                isSelected = true,
            )
        }
    }
}
