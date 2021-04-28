package com.derek.test.mainview

import com.derek.test.untils.WorkingState

data class MainViewState(
    val showItems: List<UserData> = emptyList(),
    val lastUserId: Int = 0,
    val workingState: WorkingState = WorkingState.Pending
)
