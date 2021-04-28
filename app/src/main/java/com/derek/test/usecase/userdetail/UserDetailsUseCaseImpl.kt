package com.derek.test.usecase.userdetail

import com.derek.test.repository.userdetail.UserDetailsRepository
import io.reactivex.rxjava3.core.Single

class UserDetailsUseCaseImpl(
    private val userDetailsRepository: UserDetailsRepository
):UserDetailsUseCase {
    override fun getData(login: String): Single<UserDetailsUseCaseData> {
        return userDetailsRepository.getData(login)
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