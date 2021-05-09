package com.derek.test.view.mainview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.derek.test.R
import com.derek.test.presenter.main.UserData

class UserListAdapter(
    private val onItemClickListener: (data: UserData) -> Unit
) : ListAdapter<UserData, UserListViewHolder>(UserItemCallBack()) {

    private val onPositionClickListener = { position: Int ->
        if (position in 0 until itemCount) {
            onItemClickListener(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position), onPositionClickListener)
    }

    override fun onViewRecycled(holder: UserListViewHolder) {
        holder.recycler()
        super.onViewRecycled(holder)
    }
}