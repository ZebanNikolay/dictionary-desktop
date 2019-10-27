package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.domain.Language
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

    val selectedWord: Property<Word> = SimpleObjectProperty()
    val selectedLocale: Property<Language> = SimpleObjectProperty()

    init {
        words = interactor.getWords()
        selectedLocale.value = Language.NIVKH
        onSearchQueryChanged()
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
}