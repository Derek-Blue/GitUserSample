package com.derek.test.di

import com.derek.test.interactor.UserDetailsInteractor
import com.derek.test.presenter.userdetails.UserDetailsPresenter
import com.derek.test.repository.userdetail.UserDetailsRepositoryImpl
import com.derek.test.repository.userlist.UserListRepositoryImpl
import com.derek.test.usecase.userlist.UserListUseCase
import com.derek.test.usecase.userlist.UserListUseCaseImpl
import com.derek.test.userdetails.IUserDetailsView
import org.koin.dsl.module

val presenterModule = module {

    factory<UserListUseCase> { UserListUseCaseImpl(UserListRepositoryImpl(get())) }

    factory { (view: IUserDetailsView) ->
        UserDetailsPresenter(view, UserDetailsInteractor(UserDetailsRepositoryImpl(get())))
    }
}