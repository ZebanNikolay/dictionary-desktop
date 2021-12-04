package com.ncbs.dictionary.data

import com.ncbs.dictionary.domain.Language
import com.ncbs.dictionary.domain.LocaleData
import com.ncbs.dictionary.domain.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.*
import tornadofx.FX.Companion.log
import tornadofx.Rest
import java.io.File
import java.io.IOException
import java.util.*
import java.util.logging.Logger
import kotlin.coroutines.suspendCoroutine

private const val WORDS_FILE_NAME = "words.json"
private const val WORDS_FILE_URL = "https://dictionary-f4cbd.firebaseapp.com/data/words.json"

class DictionaryRepository {

    private val client = OkHttpClient()

    suspend fun getWords(): List<Word> = withContext(Dispatchers.JavaFx) {
        log.info("Start read words from [$WORDS_FILE_NAME]")
        val file = File(WORDS_FILE_NAME)
        if (file.exists()) {
            val list = Json.decodeFromString<List<Word>>(file.readText())
            log.info("Read [$WORDS_FILE_NAME] successfully")
            return@withContext list
        } else {
            log.info("$WORDS_FILE_NAME isn't exist")
            return@withContext updateWords()
        }
    }

    suspend fun updateWords(): List<Word> = suspendCoroutine { continuation ->
        log.info("Start update words from server url = $WORDS_FILE_URL")
        val request = Request.Builder()
            .url(WORDS_FILE_URL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                log.info("Fetch words from server successfully")

                e.printStackTrace()
                continuation.resumeWith(Result.failure(e))
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                log.info("Fetch words from server successfully")
                val string = response.body!!.string()
                writeToFile(string)
                continuation.resumeWith(Result.success(Json.decodeFromString(string)))
            }
        })
    }

    private fun writeToFile(json: String): File {
        log.info("Start write words to [$WORDS_FILE_NAME]")
        return File(WORDS_FILE_NAME).apply {
            if (exists()) {
                log.info("[$WORDS_FILE_NAME] already exist, it has been deleted")
                delete()
            }
            createNewFile()
            writeText(json)
            log.info("Write [$WORDS_FILE_NAME] successfully")
        }
    }

    fun hasWordsData(): Boolean = File(WORDS_FILE_NAME).exists()
}