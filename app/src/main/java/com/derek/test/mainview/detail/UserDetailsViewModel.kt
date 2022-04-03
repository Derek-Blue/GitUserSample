package com.derek.test.mainview.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derek.test.usecase.userdetail.UserDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    login: String,
    private val userDetailsUseCase: UserDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserDetailsViewState())
    private val currentState get() = _state.value
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userDetailsUseCase(login)
                .fold({
                    val data = UserDetailsViewState(
                        it.avatar_url,
                        it.name,
                        it.bio,
                        it.login,
                        it.site_admin,
                        it.location,
                        it.blog
                    )
                    _state.emit(data)
                }, { e ->
                    _state.update {
                        currentState.copy(errorMessage = e.message ?: "ERROR")
                    }
                })
        }

    }
}