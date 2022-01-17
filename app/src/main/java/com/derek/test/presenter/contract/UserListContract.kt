package com.derek.test.presenter.contract

import com.derek.test.presenter.main.UserData

/**
 * 定義View與Presenter之間的互動
 */
interface UserListContract {

    interface UserListPresenter {
        suspend fun getData()
    }

    interface UserListView {
        fun onGetResult(data: List<UserData>)
        fun onProgress(showProgress: Boolean)
    }
}