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

package com.wuc656.nowinandroid.sync.status

import com.google.firebase.messaging.FirebaseMessaging
import com.wuc656.nowinandroid.core.data.repository.NewsRepository
import com.wuc656.nowinandroid.core.data.repository.TopicsRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementation of [SyncSubscriber] that uses Firebase Cloud Messaging to subscribe to topics.
 */
internal class FirebaseSyncSubscriber @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging,
) : SyncSubscriber {
    override suspend fun subscribe() {
        firebaseMessaging.subscribeToTopic(NewsRepository.QUERY_NEWS).await()
        firebaseMessaging.subscribeToTopic(TopicsRepository.QUERY_TOPICS).await()
    }
}
