package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.domain.Word
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import tornadofx.ViewModel
import tornadofx.observable
import tornadofx.observableList
import java.util.*

class DictionaryViewModel : ViewModel() {

    private val interactor = DictionaryInteractorFactory().provideInteractor()

    private val words: List<Word>
    val filteredWords: ObservableList<Word> = observableList()
    val locales: ObservableList<Locale>

    val selectedWord: Property<Word> = SimpleObjectProperty()
    val selectedLocale: Property<Locale> = SimpleObjectProperty()

    init {
        words = interactor.getWords()
        locales = interactor.getLocales().observable()
        selectedLocale.value = locales.first()
        onSearchQueryChanged()
        selectedWord.value = words.first()
    }

    fun getTranslateBySelectedLocale(word: Word?): String? {
        word ?: return null
        return word.locales[selectedLocale.value.language]?.value
    }

    fun onSearchQueryChanged(query: String? = "") {
        query ?: return
        filteredWords.clear()
        filteredWords.addAll(words.filter {
            it.getTranslate(selectedLocale.value.language)?.contains(query, true) ?: false
        })
    }
}