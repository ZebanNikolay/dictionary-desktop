package com.ncbs.dictionary.presentation

import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.layout.Background
import javafx.scene.layout.Border
import javafx.scene.layout.Priority
import tornadofx.*
import java.awt.Color

class UpdateWordsScreen : View() {

    private val viewModel = find(UpdateWordsViewModel::class)
    
    init {
        viewModel.updateWords(
            onSuccessAction = {
                this.replaceWith(MainScreen::class)
            },
            onFailAction = {
                // TODO: 02/12/2021  
            }
        )
    }

    override val root: Parent = vbox(alignment = Pos.CENTER) {
        label {
            textProperty().value = "Обновление базы слов..."
            addClass(AppStylesheet.h2)
            paddingBottom = SPACING_LARGE
        }
        progressbar() {
            vgrow = Priority.NEVER
        }
    }
}