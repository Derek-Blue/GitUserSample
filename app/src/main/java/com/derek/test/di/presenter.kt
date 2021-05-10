package com.derek.test.di

import com.derek.test.interactor.UserDetailsInteractor
import com.derek.test.interactor.UserListInteractor
import com.derek.test.view.mainview.IMainView
import com.derek.test.presenter.main.UserListPresenter
import com.derek.test.presenter.userdetails.UserDetailsPresenter
import com.derek.test.repository.userdetail.UserDetailsRepositoryImpl
import com.derek.test.repository.userlist.UserListRepositoryImpl
import com.derek.test.view.userdetails.IUserDetailsView
import org.koin.dsl.module

val presenterModule = module {

    factory { (view: IMainView) ->
        UserListPresenter(view, UserListInteractor(UserListRepositoryImpl(get())))
    }

    factory { (view: IUserDetailsView) ->
        UserDetailsPresenter(view, UserDetailsInteractor(UserDetailsRepositoryImpl(get())))
    }
}