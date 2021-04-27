package com.derek.test.service.respone

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("login")
    val login: String? = null,

    @SerializedName("site_admin")
    val site_admin: Boolean? = null
//
//    User details:
//    ● avatar_url
//    ● login
//    ● site_admin (badge)
//● Number of items
//● Paginated (optional)
//● start with since=0, page size = 20
//● Non-paginated
//● Limit to 100 users
)
