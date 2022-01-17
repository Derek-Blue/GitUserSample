package com.derek.test.repository.userlist

/**
 * 取得UserList資料來源
 */
interface UserListRepository {

    suspend fun getData(since: Int, callback: GetUserListCallBack)

    interface GetUserListCallBack {
        fun onResult(data: List<UserRepositoryData>)
    }
}