package com.ncbs.dictionary.domain

import java.util.Locale

interface DictionaryRepository {

    fun getWords(): List<Word>
    fun getLocales(): List<Locale>
}
