package com.derek.test.mainview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.derek.test.R
import com.derek.test.databinding.ActivityMainBinding
import com.derek.test.mainview.detail.UserDetailsActivity
import com.derek.test.repository.userlist.UserListRepositoryImpl.Companion.EMPTY_LOGIN
import com.derek.test.untils.EndlessRecyclerViewScrollListener
import com.derek.test.untils.WorkingState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    private val linearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val userListAdapter by lazy {
        UserListAdapter { data ->
            if (data.login != EMPTY_LOGIN) {
                val intent = UserDetailsActivity.newIntent(this, data.login)
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
        viewModel.state.map {
            it.showItems
        }.observe(this, Observer {
            userListAdapter.submitList(it)
        })

        //工作狀態
        viewModel.state.map {
            it.workingState
        }.observe(this, Observer {
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
        })
    }

}