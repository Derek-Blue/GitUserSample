package com.derek.test.mainview.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import com.bumptech.glide.Glide
import com.derek.test.R
import com.derek.test.databinding.ActivityUserDetailsBinding
import com.derek.test.repository.userlist.UserListRepositoryImpl.Companion.EMPTY_LOGIN
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailsActivity : AppCompatActivity(R.layout.activity_user_details) {

    companion object {
        private const val ARG_LOGIN = "argLogin"

        fun newIntent(context: Context, login: String): Intent {
            return Intent(context, UserDetailsActivity::class.java).apply {
                putExtra(ARG_LOGIN, login)
            }
        }
    }

    private lateinit var binding: ActivityUserDetailsBinding

    private val login by lazy {
        intent.getStringExtra(ARG_LOGIN) ?: EMPTY_LOGIN
    }

    private val viewModel by viewModel<UserDetailsViewModel> {
        parametersOf(login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()

        binding.closeImageView.setOnClickListener {
            finish()
        }
    }

    /**
     * observer Live Data
     */
    private fun observer() {
        viewModel.state
            .distinctUntilChanged()
            .observe(this, Observer {
                binding.locationConstrainLayout.isVisible = it.location.isNotBlank()
                binding.blogConstrainLayout.isVisible = it.blog.isNotBlank()

                Glide.with(this).load(it.avatar_url).into(binding.userImageView)
                binding.nameTextView.text = it.name
                binding.bioTextView.text = it.bio

                binding.loginTextView.text = it.login
                binding.badgeTextView.isVisible = it.site_admin

                binding.locationTextView.text = it.location

                binding.blogTextView.text = it.blog
            })
    }
}