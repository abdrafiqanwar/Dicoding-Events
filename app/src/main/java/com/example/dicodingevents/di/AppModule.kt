package com.example.dicodingevents.di

import com.example.dicodingevents.core.domain.usecase.EventInteractor
import com.example.dicodingevents.core.domain.usecase.EventUseCase
import com.example.dicodingevents.detail.DetailViewModel
import com.example.dicodingevents.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<EventUseCase> { EventInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}