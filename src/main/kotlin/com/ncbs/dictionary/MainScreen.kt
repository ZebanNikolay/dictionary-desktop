package com.ncbs.dictionary

import tornadofx.View
import tornadofx.hbox
import tornadofx.label

class MainScreen : View() {
    override val root = hbox {
        label("Hello world")
    }
}