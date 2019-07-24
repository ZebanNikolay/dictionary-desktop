package com.ncbs.dictionary.data

import com.ncbs.dictionary.domain.Locale
import com.ncbs.dictionary.domain.Word
import com.opencsv.*
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private const val TAB_CHARACTER = '\t'
private const val DICTIONARY_WORDS_PATH = "src/main/resources/dictionary.txt"
private const val CHARSET_NAME = "UTF-8"
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
            val locales = linkedSetOf<Locale>()
            for ((localeIndex, value) in line.drop(METADATA_COLUMN_COUNT).withIndex()) {
                locales.add(Locale(localesCodes[localeIndex], value, null))
            }
            words.add(Word(line.first(), locales))
        }
        csvReader.close()
        return words
    }

    private fun getCsvReader(): CSVReader {
        val reader = InputStreamReader(FileInputStream(DICTIONARY_WORDS_PATH), CHARSET_NAME)
        val parser = CSVParserBuilder()
            .withSeparator(TAB_CHARACTER)
            .build()
        return CSVReaderBuilder(reader)
            .withCSVParser(parser)
            .build()
    }
}