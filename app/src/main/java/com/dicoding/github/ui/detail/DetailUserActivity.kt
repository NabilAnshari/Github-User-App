package com.dicoding.github.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar_Url = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

            viewModel.setUserDetail(username)
            showLoading(true)
            viewModel.getUserDetail().observe(this) {
                if (it != null) {
                    binding.apply {
                        tvName.text = it.name
                        tvUsername.text = it.login
                        tvFollowers.text = "${it.followers} Followers"
                        tvFollowing.text = "${it.following} Following"
                        Glide.with(this@DetailUserActivity)
                            .load(it.avatar_url)
                            .centerCrop()
                            .into(ivProfile)
                        showLoading(false)
                    }
                }
            }

            var _isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUser(id)
                withContext(Dispatchers.Main){
                    if (count != null){
                        if (count>0){
                            binding.btnLike.isChecked = true
                            _isChecked = true
                        }else{
                            binding.btnLike.isChecked = false
                            _isChecked = false
                        }
                    }
                }
            }

            binding.btnLike.setOnClickListener{
                _isChecked =! _isChecked
                if (_isChecked){
                    if (avatar_Url != null) {
                        viewModel.addLiked(username, id, avatar_Url)
                    }
                }else{
                    viewModel.removeFromLiked(id)
                }
                binding.btnLike.isChecked= _isChecked
            }

            val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
            binding.apply {
                viewPager.adapter = sectionPagerAdapter
                tabs.setupWithViewPager(viewPager)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}