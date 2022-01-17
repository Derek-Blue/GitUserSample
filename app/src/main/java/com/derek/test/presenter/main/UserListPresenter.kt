package com.derek.test.presenter.main

import com.derek.test.presenter.contract.UserListContract
import com.derek.test.repository.userlist.UserListRepository
import com.derek.test.repository.userlist.UserRepositoryData

/**
 * 實作 Contract Presenter
 * 跟 Repository 取得資料後透過 Contract View 通知畫面
 */
class UserListPresenter(
    private val view: UserListContract.UserListView,
    private val repository: UserListRepository
) : UserListContract.UserListPresenter {

    private var currentId = 0

    private var currentList = emptyList<UserData>()

    override suspend fun getData() {
        view.onProgress(true)
        repository.getData(currentId, object : UserListRepository.GetUserListCallBack {
            override fun onResult(data: List<UserRepositoryData>) {
                val result = data.map {
                    UserData(
                        it.id, it.login, it.avatar_url, it.site_admin
                    )
                }

                currentId = result.lastOrNull()?.id ?: 0
                currentList = result + currentList

                view.onGetResult(currentList)
                view.onProgress(false)
            }
        })
    }
}