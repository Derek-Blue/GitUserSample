package com.derek.test.di

import com.derek.test.mainview.MainActivityViewModel
import com.derek.test.mainview.detail.UserDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainActivityViewModel(get()) }

    viewModel { (login: String) ->
        UserDetailsViewModel(login,get())
    }
}