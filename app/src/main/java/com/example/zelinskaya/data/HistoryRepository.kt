package com.example.zelinskaya.data

import com.example.zelinskaya.model.Meme
import io.paperdb.Paper

object HistoryRepository {

    fun saveJokes(key: String, joke: MutableList<Meme>) {
        Paper.book().write(key, joke)
    }

    fun getJokes(key: String): MutableList<Meme> {
        return Paper.book().read(key, mutableListOf())
    }

    fun getListSize(key: String): Int {
        val list: MutableList<Meme> = getJokes(key)
        return list.size
    }
}