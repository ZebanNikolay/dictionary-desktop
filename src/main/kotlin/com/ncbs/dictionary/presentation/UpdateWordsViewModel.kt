package com.ncbs.dictionary.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import tornadofx.ViewModel
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class UpdateWordsViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.JavaFx

    private val interactor = DictionaryInteractorFactory().provideInteractor()

    fun updateWords(onSuccessAction: () -> Unit, onFailAction: () -> Unit) = launch {
        try {
            interactor.updateWords()
            onSuccessAction()
        } catch (e: Exception) {
            onFailAction()
        }
    }

    fun hasLocalWords(): Boolean = interactor.hasWordsData()
}