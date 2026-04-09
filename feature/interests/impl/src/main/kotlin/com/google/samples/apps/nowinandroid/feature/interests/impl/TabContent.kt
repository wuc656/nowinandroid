/*
 * Copyright 2022 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid.feature.interests.impl

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.google.samples.apps.nowinandroid.core.designsystem.component.scrollbar.DraggableScrollbar
import com.google.samples.apps.nowinandroid.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.google.samples.apps.nowinandroid.core.designsystem.component.scrollbar.scrollbarState
import com.google.samples.apps.nowinandroid.core.model.data.FollowableTopic
import com.google.samples.apps.nowinandroid.core.ui.InterestsItem

// 翻譯對應表
private val topicTranslationMap = mapOf(
    "Android Studio" to "Android 開發工具",
    "Compose" to "介面開發 (Compose)",
    "Kotlin" to "Kotlin 語言",
    "Performance" to "效能優化",
    "Architecture" to "架構設計",
    "Testing" to "測試與品質",
    "UI & UX" to "使用者介面與體驗",
    "Gradle" to "自動化建置 (Gradle)",
    "Accessibility" to "無障礙輔助",
    "Modularization" to "模組化設計",
    "Data Storage" to "數據儲存",
    "Dependency Injection" to "依賴注入",
    "Security" to "安全與資安",
    "WorkManager" to "後台任務 (WorkManager)",
    "Camera & Media" to "相機與媒體",
    "New APIs" to "新 API 介紹",
    "App Quality" to "應用程式品質"
)

private val descriptionTranslationMap = mapOf(
    "Latest news on Android Studio" to "關於 Android Studio 的最新動態",
    "Latest news on Jetpack Compose" to "關於 Jetpack Compose 的最新動態",
    "Latest news on Kotlin" to "關於 Kotlin 語言的最新消息",
    "Latest news on Architecture" to "關於 Android 架構設計的最新消息"
)

@Composable
fun TopicsTabContent(
    topics: List<FollowableTopic>,
    onTopicClick: (String) -> Unit,
    onFollowButtonClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    withBottomSpacer: Boolean = true,
    selectedTopicId: String? = null,
    shouldHighlightSelectedTopic: Boolean = false,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val scrollableState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .testTag(LIST_PANE_TEST_TAG),
            contentPadding = PaddingValues(vertical = 16.dp),
            state = scrollableState,
        ) {
            topics.forEach { followableTopic ->
                val topicId = followableTopic.topic.id
                item(key = topicId) {
                    val isSelected = shouldHighlightSelectedTopic && topicId == selectedTopicId
                    val translatedName = topicTranslationMap[followableTopic.topic.name] ?: followableTopic.topic.name
                    val translatedDescription = descriptionTranslationMap[followableTopic.topic.shortDescription] ?: followableTopic.topic.shortDescription

                    InterestsItem(
                        name = translatedName,
                        following = followableTopic.isFollowed,
                        description = translatedDescription,
                        topicImageUrl = followableTopic.topic.imageUrl,
                        onClick = { onTopicClick(topicId) },
                        onFollowButtonClick = { onFollowButtonClick(topicId, it) },
                        isSelected = isSelected,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            if (withBottomSpacer) {
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
        }
        val scrollbarState = scrollableState.scrollbarState(
            itemsAvailable = topics.size,
        )
        scrollableState.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = scrollableState.rememberDraggableScroller(
                itemsAvailable = topics.size,
            ),
        )
    }
}

val LIST_PANE_TEST_TAG = "interests:topics"
