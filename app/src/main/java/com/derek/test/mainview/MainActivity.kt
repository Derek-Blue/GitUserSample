package com.derek.test.mainview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.derek.test.R
import com.derek.test.databinding.ActivityMainBinding
import com.derek.test.mainview.detail.UserDetailsPagerActivity
import com.derek.test.repository.userlist.UserListRepositoryImpl.Companion.EMPTY_LOGIN
import com.derek.test.untils.EndlessRecyclerViewScrollListener
import com.derek.test.untils.WorkingState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    private val linearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val userListAdapter by lazy {
        UserListAdapter { data ->
            val logins = (binding.userListRecyclerView.adapter as UserListAdapter).currentList.map {
                it.login
            }
            val position = logins.indexOf(data.login)
            if (data.login != EMPTY_LOGIN) {
                val intent = UserDetailsPagerActivity.newIntent(this, logins, position)
                startActivity(intent)
            }
        }
    }

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()

        binding.userListRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = userListAdapter
            addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.fetchMoreData()
                }
            })
        }
    }

    /**
     * observer Live Data
     */
    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.map {
                        it.showItems
                    }
                        .distinctUntilChanged()
                        .collectLatest {
                            userListAdapter.submitList(it)
                        }
                }

                launch {
                    viewModel.state.map {
                        it.workingState
                    }
                        .distinctUntilChanged()
                        .collectLatest {
                            when (it) {
                                WorkingState.Loading -> {
                                    binding.progressBar.isVisible = true
                                    binding.errorTextView.isVisible = false
                                }
                                is WorkingState.Error -> {
                                    binding.progressBar.isVisible = false
                                    binding.errorTextView.isVisible = true
                                    binding.errorTextView.text = it.whatHappened
                                }
                                else -> {
                                    binding.progressBar.isVisible = false
                                    binding.errorTextView.isVisible = false
                                }
                            }
                        }
                }
            }
        }
    }

}