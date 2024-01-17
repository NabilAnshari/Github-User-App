package com.dicoding.github.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.restaurantreview.databinding.ActivityLikedBinding
import com.dicoding.github.data.local.BookmarkLikedUser
import com.dicoding.github.data.response.User
import com.dicoding.github.ui.detail.DetailUserActivity
import com.dicoding.github.ui.main.UserAdapter


class LikedUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLikedBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: LikedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTampilan()
        setupItemViewModel()
        observeLikedUsers()
    }

    private fun setupTampilan() {
        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClick(data: User) {
                navigate(data)
            }
        })

        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@LikedUsersActivity)
            adapter = this@LikedUsersActivity.adapter
        }
    }

    private fun setupItemViewModel() {
        viewModel = ViewModelProvider(this)[LikedViewModel::class.java]
    }

    private fun observeLikedUsers() {
        viewModel.getLikedAccount()?.observe(this) { favoriteUsers ->
            favoriteUsers?.let {
                val userList = listMap(it)
                adapter.setList(userList)
            }
        }
    }

    private fun listMap(favoriteUsers: List<BookmarkLikedUser>): ArrayList<User> {
        return ArrayList(favoriteUsers.map { user ->
            User(user.login, user.id, user.avatar_url)
        })
    }

    private fun navigate(data: User) {
        Intent(this@LikedUsersActivity, DetailUserActivity::class.java).apply {
            putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
            putExtra(DetailUserActivity.EXTRA_ID, data.id)
            putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
            startActivity(this)
        }
    }
}