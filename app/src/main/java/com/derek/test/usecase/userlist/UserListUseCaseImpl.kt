package com.derek.test.usecase.userlist

import com.derek.test.repository.userlist.UserListRepository

class UserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : UserListUseCase {

    override suspend fun getFirstData(): Result<List<UserUseCaseData>> {
        return userListRepository(0)
            .map { repositoryData ->
                repositoryData.map {
                    UserUseCaseData(
                        it.id, it.login, it.avatar_url, it.site_admin
                    )
                }
            }
    }

    override suspend fun fetchMore(lastId: Int): Result<List<UserUseCaseData>> {
        return userListRepository(lastId)
            .map { repositoryData ->
                repositoryData.map {
                    UserUseCaseData(
                        it.id, it.login, it.avatar_url, it.site_admin
                    )
                }
            }
    }
}