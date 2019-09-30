package com.ncbs.dictionary.presentation

import com.sun.org.apache.xalan.internal.xsltc.compiler.Stylesheet
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