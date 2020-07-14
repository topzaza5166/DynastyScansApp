package com.dynasty.dynastyscansapp.ui.chapterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository

class ChapterListViewModel(private val repository: ServiceRepository) : ViewModel() {

    val resource: LiveData<Resource<ChapterListModel>> by lazy {
        repository.getChapterList()
    }

    val chapterList: LiveData<List<ChapterListModel.Chapter>?> = Transformations.map(resource) {
        if (it.isSuccess()) it.data?.chapters else null
    }

}