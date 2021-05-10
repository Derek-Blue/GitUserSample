package com.derek.test

import com.derek.test.interactor.UserDetailsInteractor
import com.derek.test.presenter.userdetails.DetailsData
import com.derek.test.presenter.userdetails.UserDetailsPresenter
import com.derek.test.view.userdetails.IUserDetailsView
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserDetailsPresenterTest {

    @MockK
    lateinit var view: IUserDetailsView

    @MockK
    lateinit var interactor: UserDetailsInteractor

    private lateinit var presenter: UserDetailsPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        presenter = UserDetailsPresenter(view, interactor)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun success() {
        val response = DetailsData(
            avatar_url = "https...1?v=4",
            name = "mojombo",
            bio = "",
            login = "mojombo",
            site_admin = false,
            location = "San Francisco",
            blog = "https://api.github.com/users/octocat"
        )

        every {
            view.setData(any())
        } just runs

        presenter.onSuccess(response)

        verify {
            view.setData(any())
        }
    }

    @Test
    fun fail() {
        val error = "error"
        every {
            view.showError(any())
        } just runs

        presenter.onFail(error)

        verify {
            view.showError(any())
        }
    }

}