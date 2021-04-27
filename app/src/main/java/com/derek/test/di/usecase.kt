package com.derek.test.di

import com.derek.test.repository.userlist.UserListRepositoryImpl
import com.derek.test.usecase.userlist.UserListUseCase
import com.derek.test.usecase.userlist.UserListUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserListUseCase> { UserListUseCaseImpl(UserListRepositoryImpl(get())) }
}