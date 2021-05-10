package com.derek.test.view.mainview

import androidx.recyclerview.widget.DiffUtil
import com.derek.test.presenter.main.UserData

class UserItemCallBack: DiffUtil.ItemCallback<UserData>() {

    override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
        return oldItem == newItem
    }
}