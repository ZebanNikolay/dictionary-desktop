package com.ncbs.dictionary.data

import com.ncbs.dictionary.domain.DictionaryRepository
import com.ncbs.dictionary.domain.Word
import java.util.*

class DictionaryRepositoryImpl(
    private val dataSource: WordsDataSource
) : DictionaryRepository {

    override fun getWords(): List<Word> = dataSource.getWords()

    override fun getLocales(): List<Locale> =
        listOf(Locale("ru", "RU"), Locale.US, Locale("nv", "RU"))
}