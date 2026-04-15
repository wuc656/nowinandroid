package com.wuc656.nowinandroid.core.data.repository

import com.wuc656.nowinandroid.core.data.Synchronizer
import com.wuc656.nowinandroid.core.model.data.Topic
import com.wuc656.nowinandroid.core.network.NiaNetworkDataSource
import com.wuc656.nowinandroid.core.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MockTopicsRepository @Inject constructor(
    private val network: NiaNetworkDataSource,
) : TopicsRepository {
    override fun getTopics(): Flow<List<Topic>> = flow {
        emit(network.getTopics().map { it.asExternalModel() })
    }

    override fun getTopic(id: String): Flow<Topic> = flow {
        val topics = network.getTopics(listOf(id))
        emit(topics.first().asExternalModel())
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true
}
