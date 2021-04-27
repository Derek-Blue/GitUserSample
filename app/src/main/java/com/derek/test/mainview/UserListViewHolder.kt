package com.derek.test.mainview

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.derek.test.databinding.ItemUserBinding

class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemUserBinding.bind(view)

    fun bind(item: UserData) {
        binding.badgeTextView.isVisible = item.site_admin
        binding.loginTextView.text = item.login
        Glide.with(itemView.context).load(item.avatar_url).into(binding.userImageView)
    }
}