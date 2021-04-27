package com.derek.test.mainview

data class MainViewState(
    val showItems: List<UserData> = emptyList(),
    val lastUserId: Int = 0
)
