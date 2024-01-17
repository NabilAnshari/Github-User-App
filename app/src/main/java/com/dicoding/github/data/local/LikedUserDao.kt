package com.dicoding.github.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LikedUserDao {

    @Insert
    fun addToLiked(bookmarkLikedUser: BookmarkLikedUser)

    @Query("SELECT * FROM liked_user")
    fun getLikedUser(): LiveData<List<BookmarkLikedUser>>

    @Query("SELECT count(*) FROM liked_user WHERE id = :userId")
    fun checkUser(userId: Int): Long

    @Query("DELETE FROM liked_user WHERE id = :userId")
    fun removeFromLiked(userId: Int): Int
}