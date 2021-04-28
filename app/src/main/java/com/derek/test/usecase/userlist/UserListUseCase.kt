package com.derek.test.usecase.userlist

import io.reactivex.rxjava3.core.Single

interface UserListUseCase {

    /**
     * 取得初始資料
     */
    fun getFirstData(): Single<List<UserUseCaseData>>

    /**
     * 下滑時取得更多
     */
    fun fetchMore(since: Int): Single<List<UserUseCaseData>>
}