package com.derek.test.view.mainview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.derek.test.R
import com.derek.test.databinding.ActivityMainBinding
import com.derek.test.presenter.main.UserData
import com.derek.test.presenter.main.UserListPresenter
import com.derek.test.view.userdetails.UserDetailsPagerActivity
import com.derek.test.repository.userlist.UserListRepositoryImpl.Companion.EMPTY_LOGIN
import com.derek.test.untils.EndlessRecyclerViewScrollListener
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(R.layout.activity_main), IMainView {

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

    private val presenter by inject<UserListPresenter> {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.fetchData()
        binding.userListRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = userListAdapter
            addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    presenter.fetchData()
                }
            })
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setUserListData(data: List<UserData>) {
        userListAdapter.submitList(data)
    }

    override fun showError(error: String) {
        binding.userListRecyclerView.isVisible = false
        binding.errorTextView.isVisible = true
        binding.errorTextView.text = error
    }

    override fun showProgress() {
        binding.progressBar.isVisible = true
    }

    override fun hideProgress() {
        binding.progressBar.isVisible = false
    }

}