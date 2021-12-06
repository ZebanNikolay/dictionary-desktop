package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.data.HOST_URL
import com.ncbs.dictionary.domain.Language
import com.ncbs.dictionary.domain.Word
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.stage.StageStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.ViewModel
import tornadofx.observableListOf
import tornadofx.onChange

class DictionaryViewModel : ViewModel() {

    private val interactor = DictionaryInteractorFactory().provideInteractor()

    private lateinit var words: List<Word>
    val filteredWords: ObservableList<Word> = observableListOf()

    val selectedWord: Property<Word> = SimpleObjectProperty()
    private var audio: MediaPlayer? = null
    val isPlayButtonVisible: Property<Boolean> = SimpleBooleanProperty(false)
    val selectedLocale: Property<Language> = SimpleObjectProperty()

    init {
        selectedLocale.value = Language.NIVKH
        selectedWord.onChange {
            val path = it?.locales?.get(Language.NIVKH.code)?.audioPath
            isPlayButtonVisible.value = path != null
            audio = MediaPlayer(Media("$HOST_URL$path"))
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                words = interactor.getWords()
                onSearchQueryChanged()
                selectedWord.value = words.first()
            } catch (e: Exception) {
                find<ErrorDialog>().openModal(stageStyle = StageStyle.UTILITY)
            }
        }
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