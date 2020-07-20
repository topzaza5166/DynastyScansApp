package com.dynasty.dynastyscansapp.utils

import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.orhanobut.hawk.Hawk

object Preferences {

    var latestChapter: ChapterListModel?
        get() = Hawk.get("LatestChapter")
        set(value) {
            Hawk.put("LatestChapter", value)
        }

}