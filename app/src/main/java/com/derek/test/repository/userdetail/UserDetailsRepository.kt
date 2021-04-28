package com.derek.test.repository.userdetail

import io.reactivex.rxjava3.core.Single

/**
 * 取得User Details 資料來源
 */
interface UserDetailsRepository {

    fun getData(userName: String): Single<UserDetailsRepositoryData>
}