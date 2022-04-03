package com.derek.test.repository.userdetail

import com.derek.test.service.GitUserService
import com.derek.test.service.checkIsSuccessful
import com.derek.test.service.requireBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDetailsRepositoryImpl(
    private val gitUserService: GitUserService
) : UserDetailsRepository {

    override suspend fun invoke(userName: String): Result<UserDetailsRepositoryData> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                gitUserService.getUserDetails(userName)
                    .checkIsSuccessful()
                    .requireBody()
            }
        }
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