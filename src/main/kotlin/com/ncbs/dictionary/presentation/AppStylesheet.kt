package com.ncbs.dictionary.presentation

import javafx.scene.text.FontPosture
import tornadofx.*

const val SPACING_MEDIUM = 16
const val SPACING_LARGE = 32

class AppStylesheet : Stylesheet() {

    companion object {
        val h1 by cssclass()
        val h2 by cssclass()
        val subtitle by cssclass()
        val content by cssclass()
        val onBoardingButtonHover by csspseudoclass()
    }

    init {
        h1 {
            fontSize = 48.px
        }
        h2 {
            fontSize = 24.px
        }
        subtitle {
            fontSize = 14.px
            fontStyle = FontPosture.ITALIC
        }
        content {
            fontSize = 18.px
        }
        onBoardingButtonHover {
            and(hover) {
                borderRadius = multi(box(24.px))
            }
        }
    }
}