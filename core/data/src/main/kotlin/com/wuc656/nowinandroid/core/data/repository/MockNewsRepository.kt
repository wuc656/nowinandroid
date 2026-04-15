package com.wuc656.nowinandroid.core.data.repository

import com.wuc656.nowinandroid.core.data.Synchronizer
import com.wuc656.nowinandroid.core.data.model.asExternalModel
import com.wuc656.nowinandroid.core.model.data.NewsResource
import com.wuc656.nowinandroid.core.network.NiaNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MockNewsRepository @Inject constructor(
    private val network: NiaNetworkDataSource,
) : NewsRepository {
    override fun getNewsResources(query: NewsResourceQuery): Flow<List<NewsResource>> = flow {
        val topics = network.getTopics()
        val news = network.getNewsResources(query.filterNewsIds?.toList())
        emit(news.map { it.asExternalModel(topics) })
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true
}
