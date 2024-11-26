package com.prisma.prismavi.api

import com.prisma.prismavi.api.model.ColorResponse
import com.prisma.prismavi.api.model.LoginRequest
import com.prisma.prismavi.api.model.LoginResponse
import com.prisma.prismavi.api.model.RegisterRequest
import com.prisma.prismavi.api.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("api/mock/search-color")
    @Headers("Content-Type: text/plain")
    fun getColorInfo(@Body hex: String): Call<ColorResponse>
}