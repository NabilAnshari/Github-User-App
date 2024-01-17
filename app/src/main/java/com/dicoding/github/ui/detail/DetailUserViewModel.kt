package com.dicoding.github.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.github.data.local.BookmarkLikedUser
import com.dicoding.github.data.local.LikedUserDao
import com.dicoding.github.data.local.UserDatabase
import com.dicoding.github.data.response.DetailUserResponse
import com.dicoding.github.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: LikedUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao= userDb?.favoriteUserDao()
    }

    fun setUserDetail(username:String){
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }

    fun addLiked(username: String, id: Int, avatar_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = BookmarkLikedUser(
                username,
                id,
                avatar_url
            )
            userDao?.addToLiked(user)
        }
    }

    fun checkUser(id: Int)= userDao?.checkUser(id)

    fun removeFromLiked(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromLiked(id)
        }
    }

}