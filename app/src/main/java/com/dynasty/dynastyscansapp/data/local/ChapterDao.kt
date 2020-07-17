package com.dynasty.dynastyscansapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dynasty.dynastyscansapp.data.entity.Chapter

@Dao
interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(vararg type: Chapter)

    @Query("SELECT * FROM chapter")
    suspend fun getAllChapter(): List<Chapter>

    @Query("SELECT * FROM chapter WHERE permalink LIKE :permalink")
    suspend fun findByLink(permalink: String): Chapter?

}