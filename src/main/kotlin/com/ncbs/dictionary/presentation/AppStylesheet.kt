package com.ncbs.dictionary.presentation

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class AppStylesheet : Stylesheet() {

    companion object {
        val container by cssclass()
    }

    init {
        container {
            padding = box(10.px)
        }
    }
}