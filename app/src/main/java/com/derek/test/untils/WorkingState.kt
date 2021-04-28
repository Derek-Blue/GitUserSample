package com.derek.test.untils

sealed class WorkingState {
    data class Error(val whatHappened: String = "", val throwable: Throwable? = null) : WorkingState()
    object Finished : WorkingState()
    object Loading : WorkingState()
    object Pending : WorkingState()
}