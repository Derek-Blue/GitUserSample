package com.derek.test.usecase.userlist

interface UserListUseCase {

    /**
     * 取得初始資料
     */
    suspend fun getFirstData(): Result<List<UserUseCaseData>>

    /**
     * 下滑時取得更多
     */
    suspend fun fetchMore(since: Int): Result<List<UserUseCaseData>>
}