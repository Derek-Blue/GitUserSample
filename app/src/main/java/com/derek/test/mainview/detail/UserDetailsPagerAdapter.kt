package com.derek.test.mainview.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserDetailsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val logins: List<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return logins.size
    }

    override fun createFragment(position: Int): Fragment {
        return UserDetailsFragment.newInstance(logins[position])
    }
}