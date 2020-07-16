package com.dynasty.dynastyscansapp.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import kotlin.coroutines.CoroutineContext

class ChapterDataSourceFactory(
    private val coroutineContext: CoroutineContext,
    private val service: ServiceApi,
    private val resource: MutableLiveData<Resource<ChapterListModel>>
) : DataSource.Factory<Int, ChapterModel>() {

    lateinit var dataSource: ChapterDataSource

    override fun create(): DataSource<Int, ChapterModel> {
        dataSource = ChapterDataSource(coroutineContext, service, resource)
        return dataSource
    }
}