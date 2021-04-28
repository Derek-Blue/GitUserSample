package com.derek.test.repository.userdetail

import com.derek.test.service.GitUserService
import io.reactivex.rxjava3.core.Single

class UserDetailsRepositoryImpl(
    private val gitUserService: GitUserService
) : UserDetailsRepository {

    override fun getData(userName: String): Single<UserDetailsRepositoryData> {
        return gitUserService.getUserDetails(userName)
            .map {
                UserDetailsRepositoryData(
                    it.avatar_url ?: "",
                    it.name ?: "",
                    it.bio ?: "",
                    it.login ?: "",
                    it.site_admin ?: false,
                    it.location ?: "",
                    it.blog ?: ""
                )
            }
    }
}