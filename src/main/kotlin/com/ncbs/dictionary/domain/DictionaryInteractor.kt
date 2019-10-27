package com.ncbs.dictionary.domain

interface DictionaryInteractor {

    fun getWords(): List<Word>
}

class DictionaryInteractorImpl(
    private val repository: DictionaryRepository
) : DictionaryInteractor {

    override fun getWords(): List<Word> =
        repository.getWords()
}