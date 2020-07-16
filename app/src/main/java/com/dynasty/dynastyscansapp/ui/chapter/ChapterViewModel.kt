package com.dynasty.dynastyscansapp.ui.chapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dynasty.dynastyscansapp.data.model.ChapterDetailModel

class ChapterViewModel : ViewModel() {

    enum class ViewMode {
        HORIZONTAL, VERTICAL
    }

    val chapter: MutableLiveData<ChapterDetailModel> = MutableLiveData()

    val mode: MutableLiveData<ViewMode> = MutableLiveData(ViewMode.HORIZONTAL)

    fun switchMode() {
        mode.value = if (mode.value == ViewMode.HORIZONTAL)
            ViewMode.VERTICAL
        else
            ViewMode.HORIZONTAL
    }
}