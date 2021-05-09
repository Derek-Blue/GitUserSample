package com.derek.test.mainview.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import com.bumptech.glide.Glide
import com.derek.test.R
import com.derek.test.databinding.FragmentUserDetailsBinding
import com.derek.test.repository.userlist.UserListRepositoryImpl.Companion.EMPTY_LOGIN
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

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

    private val viewModel by viewModel<UserDetailsViewModel> {
        parametersOf(login)
    }

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentUserDetailsBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observerViewState()
    }

    /**
     * observer Live Data
     */
    private fun observerViewState() {
        viewModel.state
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, Observer {
                binding.locationConstrainLayout.isVisible = it.location.isNotBlank()
                binding.blogConstrainLayout.isVisible = it.blog.isNotBlank()

                Glide.with(this).load(it.avatar_url).into(binding.userImageView)
                binding.nameTextView.text = it.name
                binding.bioTextView.text = it.bio

                binding.loginTextView.text = it.login
                binding.badgeTextView.isVisible = it.site_admin

                binding.locationTextView.text = it.location

                binding.blogTextView.text = it.blog

                if (it.errorMessage.isNotEmpty()) {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
    }
}