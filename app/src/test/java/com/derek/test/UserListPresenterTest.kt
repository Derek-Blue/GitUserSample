package com.derek.test

import com.derek.test.interactor.UserListInteractor
import com.derek.test.presenter.main.UserData
import com.derek.test.presenter.main.UserListPresenter
import com.derek.test.view.mainview.IMainView
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserListPresenterTest {

    @MockK
    lateinit var iMainView: IMainView

    @MockK
    lateinit var interactor: UserListInteractor

    private lateinit var presenter: UserListPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        presenter = UserListPresenter(iMainView, interactor)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun success() {
        val response = listOf(
            UserData(
                1, "mojombo", "https...1?v=4", false
            ),
            UserData(
                2, "defunkt", "https...2?v=4", false
            ),
            UserData(
                3, "pjhyett", "https...3?v=4", false
            ),
            UserData(
                4, "aaa", "https...3?v=4", false
            ),
            UserData(
                5, "bbb", "https...3?v=4", false
            )
        )
        every {
            iMainView.hideProgress()
        } just runs

        every {
            iMainView.setUserListData(any())
        } just runs

        presenter.onSuccess(response)

        verify {
            iMainView.setUserListData(any())
        }
    }

    @Test
    fun fail() {
        val error = "error"
        every {
            iMainView.hideProgress()
        } just runs

        every {
            iMainView.showError(any())
        } just runs

        presenter.onFail(error)

        verify {
            iMainView.showError(any())
        }
    }
}