package com.derek.test.mainview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.derek.test.untils.WorkingState
import com.derek.test.usecase.userlist.UserListUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityViewModel(
    private val userListUseCase: UserListUseCase
) : ViewModel() {

    private val _state = MutableLiveData(MainViewState())
    private val currentState get() = _state.value!!
    val state: LiveData<MainViewState> get() = _state

    private val disposables by lazy {
        CompositeDisposable()
    }

    private val fetchNewDataDisposables by lazy {
        CompositeDisposable()
    }

    init {
        getUserList()
    }

    /**
     * 取得第一筆資料
     */
    private fun getUserList() {
        userListUseCase.getFirstData()
            .subscribeOn(Schedulers.io())
            .map {
                val showItems = it.map { useCase ->
                    UserData(useCase.id, useCase.login, useCase.avatar_url, useCase.site_admin)
                }
                val lastUserId = it.last().id
                currentState.copy(
                    showItems = showItems,
                    lastUserId = lastUserId
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _state.value = currentState.copy(workingState = WorkingState.Loading)
            }
            .subscribeBy ({
                _state.value = currentState.copy(workingState = WorkingState.Error(it.message ?: "ERROR"))
            },{
                _state.value = it.copy(workingState = WorkingState.Finished)
            })
            .addTo(disposables)
    }

    /**
     * 取得更多資料
     */
    fun fetchMoreData() {
        //限制不超過100筆
        if (currentState.showItems.size >= 100) {
            fetchNewDataDisposables.dispose()
            return
        }
        userListUseCase.fetchMore(currentState.lastUserId)
            .map {
                val newItems = currentState.showItems + it.map { useCase ->
                    UserData(useCase.id, useCase.login, useCase.avatar_url, useCase.site_admin)
                }
                val lastUserId = it.last().id
                currentState.copy(
                    showItems = newItems,
                    lastUserId = lastUserId
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy ({
                Log.e("TAG", "error", it)
            },{
                _state.value = it
            })
            .addTo(fetchNewDataDisposables)
    }

    override fun onCleared() {
        disposables.dispose()
        fetchNewDataDisposables.dispose()
        super.onCleared()
    }
}