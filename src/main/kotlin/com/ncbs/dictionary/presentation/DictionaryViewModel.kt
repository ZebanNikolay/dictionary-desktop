package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.domain.Word
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import tornadofx.ViewModel
import tornadofx.observable
import java.util.*

class DictionaryViewModel : ViewModel() {

    private val interactor = DictionaryInteractorFactory().provideInteractor()

    val words: ObservableList<Word>
    val locales: ObservableList<Locale>
    val selectedWord: Property<Word> = SimpleObjectProperty()
    val selectedLocale: Property<Locale> = SimpleObjectProperty()

    init {
        words = interactor.getWords().observable()
        locales = interactor.getLocales().observable()
        selectedLocale.value = locales.first()
        selectedWord.value = words.first()

    }

    fun getTranslateBySelectedLocale(word: Word?): String? {
        word ?: return null
        return word.locales[selectedLocale.value.language]?.value
    }
}