package com.derek.test.repository.userlist

/**
 * 取得UserList資料來源
 */
interface UserListRepository {

    suspend operator fun invoke(since: Int): Result<List<UserRepositoryData>>
}