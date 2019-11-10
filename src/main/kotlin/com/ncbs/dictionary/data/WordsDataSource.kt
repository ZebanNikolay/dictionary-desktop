package com.ncbs.dictionary.data

import com.ncbs.dictionary.DictionaryApp
import com.ncbs.dictionary.domain.Language
import com.ncbs.dictionary.domain.LocaleData
import com.ncbs.dictionary.domain.Word
import com.opencsv.*
import java.io.InputStreamReader
import java.util.*

private const val TAB_CHARACTER = '\t'
private const val DICTIONARY_WORDS_PATH = "/dictionary.txt"
private const val CHARSET_NAME = "UTF-8"
private const val MP3_EXT = ".mp3"
private const val METADATA_COLUMN_COUNT = 1

interface WordsDataSource {

    fun getWords(): List<Word>
}

class WordsDataSourceImpl : WordsDataSource {

    override fun getWords(): List<Word> {
        val csvReader = getCsvReader()
        val localesCodes = csvReader.readNext().drop(METADATA_COLUMN_COUNT)
        val lines = csvReader.readAll()
        val words = LinkedList<Word>()
        for (line in lines) {
            val locales = hashMapOf<String, LocaleData>()
            for ((localeIndex, value) in line.drop(METADATA_COLUMN_COUNT).withIndex()) {
                val language = Language.values().find { it.code == localesCodes[localeIndex] } ?: continue
                val audioPath = if (language == Language.NIVKH) {
                    "/audio/" + line.first() + MP3_EXT
                } else {
                    null
                }
                locales[language.code] = LocaleData(language, value, audioPath)
            }
            words.add(Word(line.first(), locales))
        }
        csvReader.close()
        return words
    }

    private fun getCsvReader(): CSVReader {
        val stream = DictionaryApp::class.java.getResourceAsStream(DICTIONARY_WORDS_PATH)
        val reader = InputStreamReader(stream, CHARSET_NAME)
        val parser = CSVParserBuilder()
            .withSeparator(TAB_CHARACTER)
            .build()
        return CSVReaderBuilder(reader)
            .withCSVParser(parser)
            .build()
    }
}