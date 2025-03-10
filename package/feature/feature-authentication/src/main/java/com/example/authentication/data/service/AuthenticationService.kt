package com.example.authentication.data.service

import com.example.authentication.data.model.AuthenToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface AuthenticationService{
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String = "Basic Y2U5ODUwNTQxNmRjNDVjYzkyZTg1Nzc4NzM0ZTg1YTQ6ZjcxZGU4Nzg1YmQ2NGRmMmIwOTViYjRkMGU5YzgwMzI=",
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String = "untitledmusic://callback"
    ): Response<AuthenToken>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/token")
    @FormUrlEncoded
    suspend fun refreshAccessToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = "ce98505416dc45cc92e85778734e85a4",
        @Field("refresh_token") refreshToken: String,
    ): Response<AuthenToken>

}

