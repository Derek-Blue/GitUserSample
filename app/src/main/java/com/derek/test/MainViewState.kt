package com.derek.test

data class MainViewState(
    val showItems: List<UserData> = emptyList(),
    val lastUserId: Int = 0
)
