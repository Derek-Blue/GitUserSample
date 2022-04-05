package com.derek.test

import com.derek.test.repository.userdetail.UserDetailsRepository
import com.derek.test.repository.userdetail.UserDetailsRepositoryData
import com.derek.test.repository.userdetail.UserDetailsRepositoryImpl
import com.derek.test.service.GitUserService
import com.derek.test.service.respone.ResponseUserDetails
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class UserDetailsUseCaseTest {

    @MockK
    lateinit var service: GitUserService

    private lateinit var repository: UserDetailsRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserDetailsRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getData() = runBlocking {
        val response = ResponseUserDetails(
            avatar_url = "https...1?v=4",
            name = "mojombo",
            bio = "",
            login = "mojombo",
            site_admin = false,
            location = "San Francisco",
            blog = "https://api.github.com/users/octocat"
        )
        coEvery { service.getUserDetails(any()) } returns Response.success(response)

        val expect = UserDetailsRepositoryData(
            response.avatar_url ?: "",
            response.name ?: "",
            response.bio ?: "",
            response.login ?: "",
            response.site_admin ?: false,
            response.location ?: "",
            response.blog ?: ""
        )

        val result = repository.invoke("mojombo")
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrNull()
        assertThat(data).isNotNull()
        assertThat(expect.name).isEqualTo("mojombo")
    }

}