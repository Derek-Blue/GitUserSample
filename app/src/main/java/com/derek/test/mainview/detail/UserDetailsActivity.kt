package com.derek.test.mainview.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.derek.test.repository.userlist.UserListRepositoryImpl.Companion.EMPTY_LOGIN
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailsActivity : AppCompatActivity() {

    companion object {
        private const val ARG_LOGIN = "argLogin"

        fun newIntent(context: Context, login: String): Intent {
            return Intent(context, UserDetailsActivity::class.java).apply {
                putExtra(ARG_LOGIN, login)
            }
        }
    }

    private val login by lazy {
        intent.getStringExtra(ARG_LOGIN) ?: EMPTY_LOGIN
    }

    private val viewModel by viewModel<UserDetailsViewModel> {
        parametersOf(login)
    }
}