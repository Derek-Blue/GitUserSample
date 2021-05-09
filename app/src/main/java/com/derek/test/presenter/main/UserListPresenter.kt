package com.derek.test.presenter.main

import com.derek.test.interactor.UserListInteractor
import com.derek.test.view.mainview.IMainView

class UserListPresenter(
    private val view: IMainView,
    private val interactor: UserListInteractor
): UserListInteractor.OnFinishListener {

    private var currentId = 0

    private var currentList = emptyList<UserData>()

    fun fetchData() {
        if (currentList.size < 100) {
            interactor.fetchData(currentId, this)
            view.showProgress()
        }
    }

    override fun onSuccess(data: List<UserData>) {
        currentList = currentList + data
        view.setUserListData(currentList)
        currentId = data.last().id
        view.hideProgress()
    }

    override fun onFail(error: String) {
        view.showError(error)
        view.hideProgress()
    }

    fun onDestroy() {
        interactor.onDestroy()
    }
}