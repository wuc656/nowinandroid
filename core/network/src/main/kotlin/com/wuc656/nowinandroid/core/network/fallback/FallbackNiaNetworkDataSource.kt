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

package com.wuc656.nowinandroid.core.network.fallback

import android.util.Log
import com.wuc656.nowinandroid.core.network.NiaNetworkDataSource
import com.wuc656.nowinandroid.core.network.demo.DemoNiaNetworkDataSource
import com.wuc656.nowinandroid.core.network.model.NetworkChangeList
import com.wuc656.nowinandroid.core.network.model.NetworkNewsResource
import com.wuc656.nowinandroid.core.network.model.NetworkTopic
import com.wuc656.nowinandroid.core.network.retrofit.RetrofitNiaNetwork
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "FallbackNetworkDataSource"

/**
 * [NiaNetworkDataSource] that attempts remote network via [RetrofitNiaNetwork] first,
 * and seamlessly falls back to bundled static assets via [DemoNiaNetworkDataSource]
 * if the network call fails (e.g. offline, timeout, server 404/500).
 */
@Singleton
internal class FallbackNiaNetworkDataSource @Inject constructor(
    private val retrofitNetwork: RetrofitNiaNetwork,
    private val demoNetwork: DemoNiaNetworkDataSource,
) : NiaNetworkDataSource {

    override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> {
        return runCatching {
            retrofitNetwork.getTopics(ids)
        }.onFailure { throwable ->
            Log.w(TAG, "getTopics remote call failed, falling back to bundled assets: ${throwable.message}")
        }.getOrElse {
            demoNetwork.getTopics(ids)
        }
    }

    override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> {
        return runCatching {
            retrofitNetwork.getNewsResources(ids)
        }.onFailure { throwable ->
            Log.w(TAG, "getNewsResources remote call failed, falling back to bundled assets: ${throwable.message}")
        }.getOrElse {
            demoNetwork.getNewsResources(ids)
        }
    }

    override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> {
        return runCatching {
            retrofitNetwork.getTopicChangeList(after)
        }.onFailure { throwable ->
            Log.w(TAG, "getTopicChangeList remote call failed, falling back to bundled assets: ${throwable.message}")
        }.getOrElse {
            demoNetwork.getTopicChangeList(after)
        }
    }

    override suspend fun getNewsResourceChangeList(after: Int?): List<NetworkChangeList> {
        return runCatching {
            retrofitNetwork.getNewsResourceChangeList(after)
        }.onFailure { throwable ->
            Log.w(TAG, "getNewsResourceChangeList remote call failed, falling back to bundled assets: ${throwable.message}")
        }.getOrElse {
            demoNetwork.getNewsResourceChangeList(after)
        }
    }
}
