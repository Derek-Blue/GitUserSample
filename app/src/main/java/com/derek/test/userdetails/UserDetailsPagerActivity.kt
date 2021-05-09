package com.derek.test.userdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.derek.test.R
import com.derek.test.databinding.ActivityUserDetailsBinding

class UserDetailsPagerActivity : AppCompatActivity(R.layout.activity_user_details) {

    companion object {
        private const val ARG_LOGINS = "argLogins"
        private const val ARG_POSITION = "argPosition"

        fun newIntent(context: Context, logins: List<String>, position: Int): Intent {
            return Intent(context, UserDetailsPagerActivity::class.java).apply {
                putExtra(ARG_LOGINS, logins.toTypedArray())
                putExtra(ARG_POSITION, position)
            }
        }
    }

    private lateinit var binding: ActivityUserDetailsBinding

    private val logins by lazy {
        intent.getStringArrayExtra(ARG_LOGINS)?.toList() ?: emptyList()
    }

    private val viewpagerPosition by lazy {
        intent.getIntExtra(ARG_POSITION, 0)
    }

    private val viewPagerAdapter by lazy {
        UserDetailsPagerAdapter(this, logins)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (logins.isEmpty()) {
            Toast.makeText(this, "Error! Please contact customer service", Toast.LENGTH_SHORT)
                .show()
            finish()
        }

        binding.closeImageView.setOnClickListener {
            finish()
        }

        binding.contentViewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.leftImageView.isVisible = position != 0
                    binding.rightImageView.isVisible = position != logins.lastIndex
                    binding.titleTextView.text = logins[position]
                }
            })
            setCurrentItem(viewpagerPosition, false)
        }

        binding.leftImageView.setOnClickListener {
            binding.contentViewPager.currentItem = binding.contentViewPager.currentItem - 1
        }

        binding.rightImageView.setOnClickListener {
            binding.contentViewPager.currentItem = binding.contentViewPager.currentItem + 1
        }
    }
}