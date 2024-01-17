package com.dicoding.github.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.data.response.User
import com.dicoding.github.data.response.UserResponse
import com.dicoding.github.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<User>>()
    private val users = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        ApiConfig.getApiService()
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
    fun fetchAllUsers(query: String) {
        ApiConfig.getApiService()
            .getAllUsers(query)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    if (response.isSuccessful) {
                        users.postValue(response.body()?.let { ArrayList(it) })
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }
    // Function to get LiveData for displaying all users
    fun getAllUsers(): LiveData<ArrayList<User>> {
        return users
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

    /*fun setUsers(usersList: ArrayList<User>) {
        users.postValue(usersList)
    }*/
}