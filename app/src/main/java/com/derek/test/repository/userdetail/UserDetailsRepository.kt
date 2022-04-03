package com.derek.test.repository.userdetail

/**
 * 取得User Details 資料來源
 */
interface UserDetailsRepository {

    suspend operator fun invoke(userName: String): Result<UserDetailsRepositoryData>
}