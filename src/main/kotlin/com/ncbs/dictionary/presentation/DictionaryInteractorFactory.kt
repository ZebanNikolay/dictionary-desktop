package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.data.DictionaryRepository
import com.ncbs.dictionary.domain.DictionaryInteractor

class DictionaryInteractorFactory {

    fun provideInteractor(): DictionaryInteractor {
        val repository = DictionaryRepository()
        return DictionaryInteractor(repository)
    }
}