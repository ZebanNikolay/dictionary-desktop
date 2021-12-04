package com.ncbs.dictionary.domain

import com.ncbs.dictionary.data.DictionaryRepository

class DictionaryInteractor(
    private val repository: DictionaryRepository
) {

    suspend fun getWords(): List<Word> = repository.getWords()

    fun hasWordsData(): Boolean = repository.hasWordsData()
}