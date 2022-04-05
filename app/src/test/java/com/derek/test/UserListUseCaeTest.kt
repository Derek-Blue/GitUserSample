package com.derek.test

import com.derek.test.repository.userlist.UserListRepository
import com.derek.test.repository.userlist.UserListRepositoryImpl
import com.derek.test.service.GitUserService
import com.derek.test.service.respone.ResponseUser
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class UserListUseCaeTest {

    @RelaxedMockK
    lateinit var service: GitUserService

    private lateinit var repository: UserListRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserListRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getFirstData() {
        runBlocking {
            setUpFirstDataResponse()
            val result = repository.invoke(0)
            assertThat(result.isSuccess).isTrue()
            val data = result.getOrThrow()
            assertThat(data).isNotEmpty()
        }
    }

    private fun setUpFirstDataResponse() {
        coEvery { service.getUsersList(any(), any()) } returns
                Response.success(
                    listOf(
                        ResponseUser(
                            1, "mojombo", "https...1?v=4", false
                        ),
                        ResponseUser(
                            2, "defunkt", "https...2?v=4", false
                        ),
                        ResponseUser(
                            3, "pjhyett", "https...3?v=4", false
                        ),
                        ResponseUser(
                            4, "aaa", "https...3?v=4", false
                        ),
                        ResponseUser(
                            5, "bbb", "https...3?v=4", false
                        ),
                        ResponseUser(
                            6, "ccc", "https...3?v=4", false
                        ),
                        ResponseUser(
                            7, "ddd", "https...3?v=4", false
                        ),
                        ResponseUser(
                            8, "eee", "https...3?v=4", false
                        ),
                        ResponseUser(
                            9, "fff", "https...3?v=4", false
                        ),
                        ResponseUser(
                            10, "ggg", "https...3?v=4", false
                        ),
                        ResponseUser(
                            11, "hhh", "https...3?v=4", false
                        ),
                        ResponseUser(
                            12, "iii", "https...3?v=4", false
                        ),
                        ResponseUser(
                            13, "jjj", "https...3?v=4", false
                        ),
                        ResponseUser(
                            14, "kkk", "https...3?v=4", false
                        ),
                        ResponseUser(
                            15, "mmm", "https...3?v=4", false
                        ),
                        ResponseUser(
                            16, "nnn", "https...3?v=4", false
                        ),
                        ResponseUser(
                            17, "ooo", "https...3?v=4", false
                        ),
                        ResponseUser(
                            18, "ppp", "https...3?v=4", false
                        ),
                        ResponseUser(
                            19, "qqq", "https...3?v=4", false
                        ),
                        ResponseUser(
                            20, "rrr", "https...3?v=4", false
                        )
                    )
                )
    }
}