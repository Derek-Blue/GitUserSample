package com.derek.test.mainview.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.derek.test.usecase.userdetail.UserDetailsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserDetailsViewModel(
    login: String,
    private val userDetailsUseCase: UserDetailsUseCase
) : ViewModel() {

    private val _state = MutableLiveData(UserDetailsViewState())
    private val currentState get() = _state.value!!
    val state: LiveData<UserDetailsViewState> get() = _state

    private val disposables by lazy {
        CompositeDisposable()
    }

    init {
        getUserDetails(login)
    }

    private fun getUserDetails(login: String) {
        userDetailsUseCase.getData(login)
            .subscribeOn(Schedulers.io())
            .map {
                UserDetailsViewState(
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
                _state.value = currentState.copy(
                    errorMessage = it.message ?: "ERROR"
                )
            },{
                _state.value = it
            })
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}