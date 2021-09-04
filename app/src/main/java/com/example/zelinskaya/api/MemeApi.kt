package com.example.zelinskaya.api

import com.example.zelinskaya.model.MemeResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MemeApi {

    @GET("{section}/0?json=true")
    fun getMemes(@Path("section") section: String): Deferred<Response<MemeResponse>>
}
