package com.derek.test.presenter.userdetails

import com.derek.test.interactor.UserDetailsInteractor
import com.derek.test.userdetails.IUserDetailsView

class UserDetailsPresenter(
    private val view: IUserDetailsView,
    private val interactor: UserDetailsInteractor
) : UserDetailsInteractor.OnFinishListener {

    fun getData(login: String) {
        interactor.getRepositoryData(login, this)
    }

    fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccess(data: DetailsData) {
        view.setData(data)
    }

    override fun onFail(error: String) {
        view.showError(error)
    }
}