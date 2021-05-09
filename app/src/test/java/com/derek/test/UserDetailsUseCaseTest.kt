package com.derek.test

import com.derek.test.repository.userdetail.UserDetailsRepository
import com.derek.test.repository.userdetail.UserDetailsRepositoryData
import com.derek.test.usecase.userdetail.UserDetailsUseCase
import com.derek.test.usecase.userdetail.UserDetailsUseCaseData
import com.derek.test.usecase.userdetail.UserDetailsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserDetailsUseCaseTest {

    @MockK
    lateinit var repository: UserDetailsRepository

    private lateinit var useCase: UserDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UserDetailsUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getData() {
        val response = UserDetailsRepositoryData(
            avatar_url = "https...1?v=4",
            name = "mojombo",
            bio = "",
            login = "mojombo",
            site_admin = false,
            location = "San Francisco",
            blog = "https://api.github.com/users/octocat"
        )
        every { repository.getData(any()) } returns Single.just(response)

        val expect = UserDetailsUseCaseData(
            response.avatar_url,
            response.name,
            response.bio,
            response.login,
            response.site_admin,
            response.location,
            response.blog
        )

        useCase.getData("mojombo")
            .test()
            .assertComplete()
            .assertValue {
                it == expect
            }
            .dispose()
    }
}