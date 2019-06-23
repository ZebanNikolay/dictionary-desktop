package com.ncbs.dictionary.domain

data class Word(
    val id: String,
    val locales: Set<Locale>
)