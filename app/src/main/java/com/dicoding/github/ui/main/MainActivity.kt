package com.dicoding.github.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.data.response.User
import com.dicoding.github.ui.detail.DetailUserActivity
import com.dicoding.github.ui.favorite.LikedUsersActivity
import com.dicoding.github.ui.theme.SettingPreferences
import com.dicoding.github.ui.theme.SubtituteTemaActivity
import com.dicoding.github.ui.theme.SubtituteTemaViewModel
import com.dicoding.github.ui.theme.ViewModelFactory
import com.dicoding.github.ui.theme.dataStore
import com.dicoding.restaurantreview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

        val preference = SettingPreferences.getInstance(application.dataStore)
        val subtituteTemaViewModel =
            ViewModelProvider(this, ViewModelFactory(preference))[SubtituteTemaViewModel::class.java]

        subtituteTemaViewModel.getTemaSubtution().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClick(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            favoritePage.setOnClickListener {
                Intent(this@MainActivity, LikedUsersActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnTemaHome.setOnClickListener {
                Intent(this@MainActivity, SubtituteTemaActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnSearch.setOnClickListener {
                searchUser()
            }

            etQuery.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getAllUsers().observe(this) { users ->
            if (users != null) {
                adapter.setList(users)
                showLoading(false)
            }
        }

        viewModel.fetchAllUsers("")

        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

/*private fun findRestaurant() {
    showLoading(true)
    val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
    client.enqueue(object : Callback<RestaurantResponse>{
        override fun onResponse(
            call: Call<RestaurantResponse>,
            response: Response<RestaurantResponse>
        ) {
            showLoading(false)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    setRestaurantData(responseBody.restaurant)
                    setReviewData(responseBody.restaurant.customerReviews)
                }
            } else {
                Log.e(TAG, "onFailure: ${response.message()}")
            }
        }
        override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
            showLoading(false)
            Log.e(TAG, "onFailure: ${t.message}")
        }
    })
}*/


/* private fun postReview(review: String) {
     showLoading(true)
     val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
     client.enqueue(object : Callback<PostReviewResponse> {
         override fun onResponse(
             call: Call<PostReviewResponse>,
             response: Response<PostReviewResponse>
         ) {
             showLoading(false)
             val responseBody = response.body()
             if (response.isSuccessful && responseBody != null) {
                 setReviewData(responseBody.customerReviews)
             } else {
                 Log.e(TAG, "onFailure: ${response.message()}")
             }
         }
         override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
             showLoading(false)
             Log.e(TAG, "onFailure: ${t.message}")
         }
     })
 }*/
