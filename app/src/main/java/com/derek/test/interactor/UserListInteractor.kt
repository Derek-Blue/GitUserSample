package com.derek.test.interactor

import com.derek.test.presenter.main.UserData
import com.derek.test.repository.userlist.UserListRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserListInteractor(
    private val userListRepository: UserListRepository
) {
    private val disposables by lazy {
        CompositeDisposable()
    }

    interface OnFinishListener {
        fun onSuccess(data: List<UserData>)
        fun onFail(error: String)
    }

    fun fetchData(since: Int, onFinishListener: OnFinishListener) {
        userListRepository.getData(since)
            .observeOn(Schedulers.io())
            .map { repositoryData ->
                repositoryData.map {
                    UserData(
                        it.id, it.login, it.avatar_url, it.site_admin
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                onFinishListener.onFail(it.message ?: "")
            }, {
                onFinishListener.onSuccess(it)
            })
            .addTo(disposables)
    }

    fun onDestroy() {
        disposables.dispose()
    }
}