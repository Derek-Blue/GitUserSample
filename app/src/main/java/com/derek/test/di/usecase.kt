package com.derek.test.di

import com.derek.test.repository.userdetail.UserDetailsRepositoryImpl
import com.derek.test.repository.userlist.UserListRepositoryImpl
import com.derek.test.usecase.userdetail.UserDetailsUseCase
import com.derek.test.usecase.userdetail.UserDetailsUseCaseImpl
import com.derek.test.usecase.userlist.UserListUseCase
import com.derek.test.usecase.userlist.UserListUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<UserListUseCase> { UserListUseCaseImpl(UserListRepositoryImpl(get())) }

    factory<UserDetailsUseCase> { UserDetailsUseCaseImpl(UserDetailsRepositoryImpl(get())) }
}