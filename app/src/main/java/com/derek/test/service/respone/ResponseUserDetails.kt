package com.derek.test.service.respone

import com.google.gson.annotations.SerializedName

data class ResponseUserDetails(

    @SerializedName("avatar_url")
    val avatar_url: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("bio")
    val bio: String? = null,

    @SerializedName("login")
    val login: String? = null,

    @SerializedName("site_admin")
    val site_admin: Boolean? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("blog")
    val blog: String? = null
)
