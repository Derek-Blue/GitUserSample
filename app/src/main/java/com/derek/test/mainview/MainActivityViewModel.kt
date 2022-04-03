package com.derek.test.mainview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derek.test.untils.WorkingState
import com.derek.test.usecase.userlist.UserListUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val userListUseCase: UserListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    private val currentState get() = _state.value
    val state = _state.asStateFlow()

    private val loadMoreFlow = MutableSharedFlow<Unit>()

    init {
        /**
         * 取得第一筆資料
         */
        viewModelScope.launch {
            flow {
                val newState = userListUseCase.getFirstData().getOrNull()?.let {
                    MainViewState(
                        showItems = it.map { useCase ->
                            UserData(
                                useCase.id,
                                useCase.login,
                                useCase.avatar_url,
                                useCase.site_admin
                            )
                        },
                        lastUserId = it.last().id
                    )
                } ?: MainViewState()
                emit(newState)
            }
                .onStart {
                    _state.update {
                        MainViewState(workingState = WorkingState.Loading)
                    }
                }
                .filter { newState ->
                    newState.showItems.isNotEmpty()
                }
                .collect { newState ->
                    _state.update {
                        newState.copy(workingState = WorkingState.Finished)
                    }
                }
        }

        viewModelScope.launch {
            loadMoreFlow
                .filter {
                    currentState.lastUserId > 0 && currentState.showItems.isNotEmpty() && currentState.showItems.size < 100
                }
                .map {
                    userListUseCase.fetchMore(currentState.lastUserId).getOrNull()?.let {
                        val nextData = it.map { useCase ->
                            UserData(
                                useCase.id,
                                useCase.login,
                                useCase.avatar_url,
                                useCase.site_admin
                            )
                        }
                        currentState.copy(
                            showItems = currentState.showItems + nextData,
                            lastUserId = it.last().id
                        )
                    } ?: currentState
                }
                .collect { newState ->
                    _state.update {
                        newState
                    }
                }
        }
    }

    fun fetchMoreData() {
        viewModelScope.launch {
            loadMoreFlow.emit(Unit)
        }
    }
}