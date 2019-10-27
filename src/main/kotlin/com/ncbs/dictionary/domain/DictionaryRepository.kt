package com.ncbs.dictionary.domain

interface DictionaryRepository {

    fun getWords(): List<Word>
}
