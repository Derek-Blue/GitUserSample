package com.derek.test.interactor

import com.derek.test.presenter.userdetails.DetailsData
import com.derek.test.repository.userdetail.UserDetailsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserDetailsInteractor(
    private val userDetailsRepository: UserDetailsRepository
) {

    private val disposables by lazy {
        CompositeDisposable()
    }

    interface OnFinishListener {
        fun onSuccess(data: DetailsData)
        fun onFail(error: String)
    }

    fun getRepositoryData(name: String, onFinishListener: OnFinishListener) {
        userDetailsRepository.getData(name)
            .observeOn(Schedulers.io())
            .map {
                DetailsData(
                    it.avatar_url,
                    it.name,
                    it.bio,
                    it.login,
                    it.site_admin,
                    it.location,
                    it.blog
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy ({
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