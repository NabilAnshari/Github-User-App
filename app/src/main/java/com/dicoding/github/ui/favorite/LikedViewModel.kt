package com.dicoding.github.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.github.data.local.BookmarkLikedUser
import com.dicoding.github.data.local.LikedUserDao
import com.dicoding.github.data.local.UserDatabase

class LikedViewModel(application: Application): AndroidViewModel(application) {

    private var likedDao: LikedUserDao?
    private var likedDb: UserDatabase?

    init {
        likedDb = UserDatabase.getDatabase(application)
        likedDao= likedDb?.favoriteUserDao()
    }

    fun getLikedAccount(): LiveData<List<BookmarkLikedUser>>?{
        return likedDao?.getLikedUser()
    }
}