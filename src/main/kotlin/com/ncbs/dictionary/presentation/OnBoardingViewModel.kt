package com.ncbs.dictionary.presentation

import tornadofx.ViewModel

class OnBoardingViewModel : ViewModel() {

    private val interactor = DictionaryInteractorFactory().provideInteractor()

    fun onEnterButtonClicked(currentScreen: OnBoardingScreen) {
        val navigateTo = if (interactor.hasWordsData()) {
            MainScreen::class
        } else {
            UpdateWordsScreen::class
        }
        currentScreen.replaceWith(navigateTo)
    }
}