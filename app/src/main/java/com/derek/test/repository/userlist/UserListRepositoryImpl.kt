package com.derek.test.repository.userlist

import com.derek.test.service.GitUserService
import com.derek.test.service.checkIsSuccessful
import com.derek.test.service.requireBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserListRepositoryImpl(
    private val gitUserService: GitUserService
) : UserListRepository {

    companion object {
        private const val DEFAULT_TAKE_COUNT = 20
        const val EMPTY_LOGIN = "unKnow"
    }

    override suspend fun invoke(since: Int): Result<List<UserRepositoryData>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                gitUserService.getUsersList(since, DEFAULT_TAKE_COUNT)
                    .checkIsSuccessful()
                    .requireBody()
            }
        }
            .map { response ->
                response.mapNotNull {
                    UserRepositoryData(
                        it.id ?: return@mapNotNull null,
                        it.login ?: EMPTY_LOGIN,
                        it.avatar_url ?: "",
                        it.site_admin ?: false
                    )
                }
            }
    }
}