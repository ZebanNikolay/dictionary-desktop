package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.data.DictionaryRepositoryImpl
import com.ncbs.dictionary.data.WordsDataSourceImpl
import com.ncbs.dictionary.domain.DictionaryInteractor
import com.ncbs.dictionary.domain.DictionaryInteractorImpl

class DictionaryInteractorFactory {

    fun provideInteractor(): DictionaryInteractor {
        val dataSource = WordsDataSourceImpl()
        val repository = DictionaryRepositoryImpl(dataSource)
        return DictionaryInteractorImpl(repository)
    }
}