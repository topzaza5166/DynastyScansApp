package com.dynasty.dynastyscansapp.di

import com.dynasty.dynastyscansapp.ui.chapterlist.ChapterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { ChapterListViewModel(get()) }
}