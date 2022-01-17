package com.derek.test.repository.userlist

import com.derek.test.service.GitUserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserListRepositoryImpl(
    private val gitUserService: GitUserService
) : UserListRepository {

    companion object {
        private const val DEFAULT_TAKE_COUNT = 20
        const val EMPTY_LOGIN = "unKnow"
    }

    override suspend fun getData(since: Int, callback: UserListRepository.GetUserListCallBack) {
        withContext(Dispatchers.Default) {
            val body = gitUserService.getUsersList(since, DEFAULT_TAKE_COUNT).body()

            body?.let { response ->
                val repositoryData = response.mapNotNull {
                    UserRepositoryData(
                        it.id ?: return@mapNotNull null,
                        it.login ?: EMPTY_LOGIN,
                        it.avatar_url ?: "",
                        it.site_admin ?: false
                    )
                }
                callback.onResult(repositoryData)
            }
        }
    }
}