package com.example.chatappwithfirebase.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface TodoApi {
    @GET("/api/tasks")
    suspend fun getAllTasks(): Response<Any>


    @POST("/api/register")
    suspend fun registerUser(): Response<Any>

}