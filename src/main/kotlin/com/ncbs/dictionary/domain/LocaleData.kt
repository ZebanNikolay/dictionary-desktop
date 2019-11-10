package com.ncbs.dictionary.domain

data class LocaleData(
    val language: Language,
    val value: String,
    val audioPath: String?
)