package com.dynasty.dynastyscansapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dynasty.dynastyscansapp.data.entity.Chapter


@Database(entities = [Chapter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chapterDao(): ChapterDao

}