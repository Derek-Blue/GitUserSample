package com.derek.test.view.mainview

import com.derek.test.presenter.main.UserData

interface IMainView {

    fun setUserListData(data: List<UserData>)

    fun showError(error: String)

    fun showProgress()

    fun hideProgress()
}