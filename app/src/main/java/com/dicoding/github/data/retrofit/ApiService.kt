package com.dicoding.github.data.retrofit


import com.dicoding.github.data.response.DetailUserResponse
import com.dicoding.github.data.response.User
import com.dicoding.github.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q")query: String
    ):Call<UserResponse>

    @GET("users")
    @Headers("Authorization: token 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")
    fun getAllUsers(
        @Query("q")query: String
    ): Call<List<User>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ):Call<ArrayList<User>>
}