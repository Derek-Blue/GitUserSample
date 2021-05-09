package com.derek.test.userdetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.derek.test.R
import com.derek.test.databinding.FragmentUserDetailsBinding
import com.derek.test.presenter.userdetails.DetailsData
import com.derek.test.presenter.userdetails.UserDetailsPresenter
import com.derek.test.repository.userlist.UserListRepositoryImpl.Companion.EMPTY_LOGIN
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class UserDetailsFragment : Fragment(R.layout.fragment_user_details), IUserDetailsView {

    companion object {
        private const val ARG_LOGIN = "argLogin"
        fun newInstance(login: String): UserDetailsFragment {
            return UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOGIN, login)
                }
            }
        }
    }

    private val login by lazy {
        arguments?.getString(ARG_LOGIN) ?: EMPTY_LOGIN
    }

    private val presenter by inject<UserDetailsPresenter> {
        parametersOf(this)
    }

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentUserDetailsBinding.bind(view)
        presenter.getData(login)
    }

    override fun onDestroy() {
        _binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setData(data: DetailsData) {
        binding.locationConstrainLayout.isVisible = data.location.isNotBlank()
        binding.blogConstrainLayout.isVisible = data.blog.isNotBlank()

        Glide.with(this).load(data.avatar_url).into(binding.userImageView)
        binding.nameTextView.text = data.name
        binding.bioTextView.text = data.bio

        binding.loginTextView.text = data.login
        binding.badgeTextView.isVisible = data.site_admin

        binding.locationTextView.text = data.location

        binding.blogTextView.text = data.blog
    }

    override fun showError(error: String) {
        if (error.isNotEmpty()) {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}