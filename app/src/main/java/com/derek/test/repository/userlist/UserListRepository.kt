package com.derek.test.repository.userlist

import io.reactivex.rxjava3.core.Single

/**
 * 取得UserList資料來源
 */
interface UserListRepository {

    fun getData(since: Int): Single<List<UserRepositoryData>>
}