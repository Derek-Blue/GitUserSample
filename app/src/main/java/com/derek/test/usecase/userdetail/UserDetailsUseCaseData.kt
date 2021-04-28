package com.derek.test.usecase.userdetail

data class UserDetailsUseCaseData(
    val avatar_url: String,
    val name: String,
    val bio: String,
    val login: String,
    val site_admin: Boolean,
    val location: String,
    val blog: String
)
