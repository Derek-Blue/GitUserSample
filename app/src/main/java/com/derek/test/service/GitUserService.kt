package com.derek.test.service

import com.derek.test.service.respone.ResponseUser
import com.derek.test.service.respone.ResponseUserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitUserService {

    /**
     * 取得Users清單
     *
     * @param lastId 取ID大於此參數的User
     * @param fetchCount 每次取最多幾筆
     */
    @GET("users")
    suspend fun getUsersList(
        @Query("since") lastId: Int,
        @Query("per_page") fetchCount: Int
    ): Response<List<ResponseUser>>

    /**
     * 取得指定user details
     *
     * @param username 指定的user
     */
    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): Response<ResponseUserDetails>
}