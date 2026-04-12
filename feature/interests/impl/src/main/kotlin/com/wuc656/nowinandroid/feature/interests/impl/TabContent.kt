package com.wuc656.nowinandroid.feature.interests.impl

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.wuc656.nowinandroid.core.designsystem.component.scrollbar.DraggableScrollbar
import com.wuc656.nowinandroid.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.wuc656.nowinandroid.core.designsystem.component.scrollbar.scrollbarState
import com.wuc656.nowinandroid.core.model.data.FollowableTopic
import com.wuc656.nowinandroid.core.ui.InterestsItem

private val topicTranslationMap = mapOf(
    "Android Studio" to "Android Studio",
    "Compose" to "Compose",
    "Kotlin" to "Kotlin",
    "Performance" to "Performance",
    "Architecture" to "Architecture",
    "Testing" to "Testing",
    "UI & UX" to "UI & UX",
    "Gradle" to "Gradle",
    "Accessibility" to "Accessibility",
    "Modularization" to "Modularization",
    "Data Storage" to "Data Storage",
    "Dependency Injection" to "Dependency Injection",
    "Security" to "Security",
    "WorkManager" to "WorkManager",
    "Camera & Media" to "Camera & Media",
)

@Composable
fun TopicsTabContent(
    topics: List<FollowableTopic>,
    onTopicClick: (String) -> Unit,
    onFollowButtonClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    selectedTopicId: String? = null,
    shouldHighlightSelectedTopic: Boolean = false,
) {
    val scrollableState = rememberLazyListState()
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = scrollableState,
            modifier = Modifier
                .testTag("interests:topics")
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize(),
        ) {
            items(
                count = topics.size,
                key = { topics[it].topic.id },
            ) { index ->
                val followableTopic = topics[index]
                val topic = followableTopic.topic
                InterestsItem(
                    name = topicTranslationMap[topic.name] ?: topic.name,
                    description = topic.shortDescription,
                    topicImageUrl = topic.imageUrl,
                    following = followableTopic.isFollowed,
                    isSelected = shouldHighlightSelectedTopic && (topic.id == selectedTopicId),
                    onClick = { onTopicClick(topic.id) },
                    onFollowButtonClick = { onFollowButtonClick(topic.id, it) },
                )
            }
        }
        scrollableState.DraggableScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            state = scrollableState.scrollbarState(
                itemsAvailable = topics.size,
            ),
            orientation = Orientation.Vertical,
            onThumbMoved = scrollableState.rememberDraggableScroller(
                itemsAvailable = topics.size,
            ),
        )
    }
}
