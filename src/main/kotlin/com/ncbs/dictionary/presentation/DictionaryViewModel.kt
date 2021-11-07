package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.domain.Language
import com.ncbs.dictionary.domain.Word
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import tornadofx.ViewModel
import tornadofx.observableList
import tornadofx.observableListOf
import tornadofx.onChange

class DictionaryViewModel : ViewModel() {

    private val interactor = DictionaryInteractorFactory().provideInteractor()

    private val words: List<Word>
    val filteredWords: ObservableList<Word> = observableListOf()

    val selectedWord: Property<Word> = SimpleObjectProperty()
    private var audio: MediaPlayer? = null
    val isPlayButtonVisible: Property<Boolean> = SimpleBooleanProperty(false)
    val selectedLocale: Property<Language> = SimpleObjectProperty()

    init {
        words = interactor.getWords()
        selectedLocale.value = Language.NIVKH
        onSearchQueryChanged()
        selectedWord.onChange {
            val path = it?.locales?.get(Language.NIVKH.code)?.audioPath
            val url = javaClass.getResource(path ?: return@onChange)
            if (url == null) {
                audio = null
                isPlayButtonVisible.value = false
                return@onChange
            }
            isPlayButtonVisible.value = true
            audio = MediaPlayer(Media(url.toExternalForm()))
        }
        selectedWord.value = words.first()
    }

    fun getTranslateBySelectedLocale(word: Word?): String? {
        word ?: return null
        return word.locales[selectedLocale.value.code]?.value
    }

    fun onSearchQueryChanged(query: String? = "") {
        query ?: return
        filteredWords.clear()
        filteredWords.addAll(words.filter {
            it.getTranslate(selectedLocale.value.code)?.contains(query, true) ?: false
        })
    }

    fun onPlay() {
        audio?.stop()
        audio?.play()
    }
}