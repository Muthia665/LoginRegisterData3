package com.example.loginregisterdata2.network

import com.example.loginregisterdata2.Models.DefaultResponse
import com.example.loginregisterdata2.Models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("createuser")
    fun createuser(
        @Field("email") email : String,
        @Field("name") name : String,
        @Field("password") password : String,
        @Field("school") school : String
    ) : Call<DefaultResponse>

    @FormUrlEncoded
    @POST("userlogin")
    fun userlogin(
        @Field("email") email : String,
        @Field("password") password: String
    ) : Call<LoginResponse>
}