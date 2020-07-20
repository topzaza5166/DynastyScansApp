package com.dynasty.dynastyscansapp.ui.chapterlist

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.datasource.ChapterDataSource
import com.dynasty.dynastyscansapp.data.datasource.ChapterDataSourceFactory
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import kotlinx.coroutines.Dispatchers

class ChapterListViewModel(repository: ServiceRepository) : ViewModel() {

    private val pageSize = 25

    val resource: MutableLiveData<Resource<ChapterListModel>> = MutableLiveData()

    var chaptersList: LiveData<PagedList<ChapterModel>>

    var dataSourceFactory: ChapterDataSourceFactory

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()

        dataSourceFactory = ChapterDataSourceFactory(viewModelScope.coroutineContext, repository, resource)

        chaptersList = LivePagedListBuilder<Int, ChapterModel>(dataSourceFactory, config).build()
    }



}