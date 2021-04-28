package com.derek.test.mainview.detail

data class UserDetailsViewState(
    val avatar_url: String = "",
    val name: String = "",
    val bio: String = "",
    val login: String = "",
    val site_admin: Boolean = false,
    val location: String = "",
    val blog: String = ""
)
