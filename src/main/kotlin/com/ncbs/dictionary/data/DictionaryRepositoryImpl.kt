package com.ncbs.dictionary.data

import com.ncbs.dictionary.domain.DictionaryRepository
import com.ncbs.dictionary.domain.Word

class DictionaryRepositoryImpl(
    private val dataSource: WordsDataSource
) : DictionaryRepository {

    override fun getWords(): List<Word> = dataSource.getWords()
}