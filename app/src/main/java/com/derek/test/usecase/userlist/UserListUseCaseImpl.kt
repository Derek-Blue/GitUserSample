package com.derek.test.usecase.userlist

import com.derek.test.repository.userlist.UserListRepository
import io.reactivex.rxjava3.core.Single

class UserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : UserListUseCase {

    override fun getFirstData(): Single<List<UserUseCaseData>> {
        return userListRepository.getData(0)
            .map { repositoryData ->
                repositoryData.map {
                    UserUseCaseData(
                        it.id, it.login, it.avatar_url, it.site_admin
                    )
                }
            }
    }

    override fun fetchMore(since: Int): Single<List<UserUseCaseData>> {
        return userListRepository.getData(since)
            .map { repositoryData ->
                repositoryData.map {
                    UserUseCaseData(
                        it.id, it.login, it.avatar_url, it.site_admin
                    )
                }
            }
    }
}