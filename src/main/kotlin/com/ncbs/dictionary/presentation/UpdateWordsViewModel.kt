package com.ncbs.dictionary.presentation

import tornadofx.ViewModel

class UpdateWordsViewModel : ViewModel() {

    private val interactor = DictionaryInteractorFactory().provideInteractor()

    fun updateWords(onSuccessAction: () -> Unit, onFailAction: () -> Unit) {
    }
}