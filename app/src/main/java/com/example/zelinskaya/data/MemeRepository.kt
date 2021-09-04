package com.example.zelinskaya.data

import com.example.zelinskaya.model.Meme
import com.example.zelinskaya.api.MemeApi

class MemeRepository(private val api : MemeApi): BaseRepository() {

    suspend fun getJokes(section: String) : MutableList<Meme>{
        val movieResponse = safeApiCall(
            call = {api.getMemes(section).await()},
            errorMessage = "Error Memes"
        )
        return movieResponse!!.result
    }
}