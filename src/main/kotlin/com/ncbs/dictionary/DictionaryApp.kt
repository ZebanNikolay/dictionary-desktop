package com.ncbs.dictionary

import com.ncbs.dictionary.presentation.AppStylesheet
import com.ncbs.dictionary.presentation.MainScreen
import jfxtras.styles.jmetro.Style
import tornadofx.App
import tornadofx.importStylesheet
import tornadofx.reloadStylesheetsOnFocus

class DictionaryApp : App(MainScreen::class, AppStylesheet::class) {

    init {
        importStylesheet(Style.LIGHT.styleStylesheetURL)
        reloadStylesheetsOnFocus()
    }
}