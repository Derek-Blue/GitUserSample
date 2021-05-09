package com.derek.test

import com.derek.test.repository.userlist.UserListRepository
import com.derek.test.repository.userlist.UserRepositoryData
import com.derek.test.usecase.userlist.UserListUseCase
import com.derek.test.usecase.userlist.UserListUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class UserListUseCaeTest {

    @MockK
    lateinit var repository: UserListRepository

    private lateinit var useCase: UserListUseCase

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UserListUseCaseImpl(repository)
        testScheduler = TestScheduler()
    }

    @After
    fun tearDown() {
        clearAllMocks()
        testScheduler.shutdown()
    }

    @Test
    fun getFirstData() {
        setUpFirstDataResponse()

        val testObserver = useCase.getFirstData()
            .subscribeOn(testScheduler)
            .test()
            .assertNoErrors()
            .assertValueCount(0) // "time" hasn't started so no value expected

        // 500 millis later
        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)

        testObserver.assertValueCount(1)
            .assertValueAt(0) {
                it.size == 20
            }
            .assertValueAt(0) {
                it == it.toSet().toList()
            }
            .dispose()
    }

    @Test
    fun fetchData() {
        setUpFetchMoreDataResponse()

        val testObserver = useCase.fetchMore(20)
            .subscribeOn(testScheduler)
            .test()
            .assertNoErrors()
            .assertValueCount(0) // "time" hasn't started so no value expected

        // 500 millis later
        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)

        testObserver.assertValueCount(1)
            .assertValueAt(0) {
                it.size == 20
            }
            .assertValueAt(0) {
                it == it.toSet().toList()
            }
            .dispose()
    }

    private fun setUpFirstDataResponse() {
        every { repository.getData(any()) } returns
                Single.just(
                    listOf(
                        UserRepositoryData(
                            1, "mojombo", "https...1?v=4", false
                        ),
                        UserRepositoryData(
                            2, "defunkt", "https...2?v=4", false
                        ),
                        UserRepositoryData(
                            3, "pjhyett", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            4, "aaa", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            5, "bbb", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            6, "ccc", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            7, "ddd", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            8, "eee", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            9, "fff", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            10, "ggg", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            11, "hhh", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            12, "iii", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            13, "jjj", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            14, "kkk", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            15, "mmm", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            16, "nnn", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            17, "ooo", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            18, "ppp", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            19, "qqq", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            20, "rrr", "https...3?v=4", false
                        )
                    )
                )
    }

    private fun setUpFetchMoreDataResponse() {
        every { repository.getData(any()) } returns
                Single.just(
                    listOf(
                        UserRepositoryData(
                            21, "mojombo", "https...1?v=4", false
                        ),
                        UserRepositoryData(
                            22, "defunkt", "https...2?v=4", false
                        ),
                        UserRepositoryData(
                            23, "pjhyett", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            24, "aaa", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            25, "bbb", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            26, "ccc", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            27, "ddd", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            28, "eee", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            29, "fff", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            30, "ggg", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            31, "hhh", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            32, "iii", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            33, "jjj", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            34, "kkk", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            35, "mmm", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            36, "nnn", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            37, "ooo", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            38, "ppp", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            39, "qqq", "https...3?v=4", false
                        ),
                        UserRepositoryData(
                            40, "rrr", "https...3?v=4", false
                        )
                    )
                )
    }
}