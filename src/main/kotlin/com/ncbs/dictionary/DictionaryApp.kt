package com.ncbs.dictionary

import com.ncbs.dictionary.presentation.AppStylesheet
import com.ncbs.dictionary.presentation.MainScreen
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus

class DictionaryApp : App(MainScreen::class, AppStylesheet::class) {

    init {
        reloadStylesheetsOnFocus()
    }
}