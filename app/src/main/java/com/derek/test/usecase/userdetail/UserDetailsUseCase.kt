package com.derek.test.usecase.userdetail

interface UserDetailsUseCase {

    suspend operator fun invoke(login: String): Result<UserDetailsUseCaseData>
}