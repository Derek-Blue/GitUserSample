package com.derek.test.service

import com.derek.test.service.respone.ResponseUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitUserService {

    /**
     * 取得Users清單
     *
     * @param since 取ID大於此參數的User
     * @param perPage 每次取最多幾筆
     */
    @GET("users")
    fun getUsersList(
        @Query("since")since: Int,
        @Query("per_page")perPage: Int
    ): Single<List<ResponseUser>>
}