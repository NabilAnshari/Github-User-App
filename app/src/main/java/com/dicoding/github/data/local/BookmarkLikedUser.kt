package com.dicoding.github.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Suppress("DEPRECATED_ANNOTATION")
@Entity(tableName = "liked_user")
data class BookmarkLikedUser(
    @ColumnInfo(name = "name")
    val login: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "avatar_url")
    val avatar_url:String
): Serializable
