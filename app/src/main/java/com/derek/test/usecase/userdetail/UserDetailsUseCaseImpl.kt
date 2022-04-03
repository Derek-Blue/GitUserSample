package com.derek.test.usecase.userdetail

import com.derek.test.repository.userdetail.UserDetailsRepository
import io.reactivex.rxjava3.core.Single

class UserDetailsUseCaseImpl(
    private val userDetailsRepository: UserDetailsRepository
):UserDetailsUseCase {

    override suspend fun invoke(login: String): Result<UserDetailsUseCaseData> {
        return userDetailsRepository(login)
            .map {
                UserDetailsUseCaseData(
                    it.avatar_url,
                    it.name,
                    it.bio,
                    it.login,
                    it.site_admin,
                    it.location,
                    it.blog
                )
            }
    }
}