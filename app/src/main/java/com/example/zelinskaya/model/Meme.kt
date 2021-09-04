package com.example.zelinskaya.model

data class Meme(
    var id: Int,
    var description: String,
    var gifURL: String
)

data class MemeResponse(
    var result: MutableList<Meme>
)