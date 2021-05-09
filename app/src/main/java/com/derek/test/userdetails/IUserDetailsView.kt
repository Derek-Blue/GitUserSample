package com.derek.test.userdetails

import com.derek.test.presenter.userdetails.DetailsData

interface IUserDetailsView {

    fun setData(data: DetailsData)

    fun showError(error: String)
}