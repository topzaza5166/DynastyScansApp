package com.dynasty.dynastyscansapp.di

import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.local.ChapterDao
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import com.dynasty.dynastyscansapp.data.repository.ServiceRepositoryImpl
import com.dynasty.dynastyscansapp.ui.chapter.ChapterViewModel
import com.dynasty.dynastyscansapp.ui.chapterlist.ChapterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { provideRepository(get(), get()) }

    viewModel { ChapterListViewModel(get()) }

    viewModel { ChapterViewModel() }
}

fun provideRepository(api: ServiceApi, dao: ChapterDao): ServiceRepository =
    ServiceRepositoryImpl(api, dao)