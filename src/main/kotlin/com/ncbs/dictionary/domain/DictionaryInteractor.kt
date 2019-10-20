package com.ncbs.dictionary.domain

import java.util.*

interface DictionaryInteractor {

    fun getWords(): List<Word>
    fun getLocales(): List<Locale>
}

class DictionaryInteractorImpl(
    private val repository: DictionaryRepository
) : DictionaryInteractor {

    override fun getWords(): List<Word> =
        repository.getWords()

    override fun getLocales(): List<Locale> =
        repository.getLocales()
}