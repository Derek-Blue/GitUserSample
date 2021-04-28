package com.derek.test.repository.userlist

import com.derek.test.service.GitUserService
import io.reactivex.rxjava3.core.Single

class UserListRepositoryImpl(
    private val gitUserService: GitUserService
) : UserListRepository {

    companion object {
        private const val DEFAULT_TAKE_COUNT = 20
        const val EMPTY_LOGIN = "unKnow"
    }

    override fun getData(since: Int): Single<List<UserRepositoryData>> {
        return gitUserService.getUsersList(since, DEFAULT_TAKE_COUNT)
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