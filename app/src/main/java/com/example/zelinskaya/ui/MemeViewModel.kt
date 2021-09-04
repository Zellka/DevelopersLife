package com.example.zelinskaya.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zelinskaya.model.Meme
import com.example.zelinskaya.api.ApiFactory
import com.example.zelinskaya.data.HistoryRepository
import com.example.zelinskaya.data.MemeRepository
import com.example.zelinskaya.utils.Constants
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MemeViewModel(index: Int) : ViewModel() {

    private val _index = MutableLiveData<Int>()

    var memesMutableLiveData: MutableLiveData<MutableList<Meme>> =
        MutableLiveData<MutableList<Meme>>()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val repository: MemeRepository = MemeRepository(ApiFactory.api)

    init {
        _index.value = index
    }

    private fun getMemes(key: String, section: String) {
        scope.launch {
            val jokes = repository.getJokes(section)
            if (jokes.size == 0) {
                if (HistoryRepository.getListSize(key) != 0)
                    memesMutableLiveData.postValue(HistoryRepository.getJokes(key))
            }
            memesMutableLiveData.postValue(jokes)
            HistoryRepository.saveJokes(key, jokes)
        }
    }

    fun getData() {
        if (_index.value == 1) {
            getMemes(Constants.LATEST_KEY, Constants.SECTION_LATEST)
        }
        if (_index.value == 2) {
            getMemes(Constants.TOP_KEY, Constants.SECTION_TOP)
        } else {
            getMemes(Constants.HOT_KEY, Constants.SECTION_HOT)
        }
    }
}

class ViewModelFactory(private val index: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Int::class.java).newInstance(index)
    }
}