package com.derek.test.usecase.userdetail

import io.reactivex.rxjava3.core.Single

interface UserDetailsUseCase {

    fun getData(login: String): Single<UserDetailsUseCaseData>
}