package com.dynasty.dynastyscansapp.di

import android.content.Context
import androidx.room.Room
import com.dynasty.dynastyscansapp.data.local.AppDatabase
import com.dynasty.dynastyscansapp.data.local.ChapterDao
import com.dynasty.dynastyscansapp.utils.Constants.DB_FILE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val database = module {

    single { provideAppDatabase(androidContext()) }

    single { provideChapterDao(get()) }
}

fun provideAppDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, DB_FILE_NAME)
        .build()

fun provideChapterDao(db: AppDatabase): ChapterDao =
    db.chapterDao()

