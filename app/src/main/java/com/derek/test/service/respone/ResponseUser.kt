package com.derek.test.service.respone

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("login")
    val login: String? = null,

    @SerializedName("site_admin")
    val site_admin: Boolean? = null
)
