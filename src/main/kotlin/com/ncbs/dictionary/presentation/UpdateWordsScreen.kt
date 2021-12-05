package com.ncbs.dictionary.presentation

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.layout.Background
import javafx.scene.layout.Border
import javafx.scene.layout.Priority
import javafx.stage.StageStyle
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
                this.replaceWith(UpdateWordsErrorScreen::class)
            }
        )
    }

    override val root: Parent = vbox(alignment = Pos.CENTER) {
        label("Обновление базы слов...") {
            addClass(AppStylesheet.h2)
            paddingBottom = SPACING_LARGE
        }
        progressbar() {
            vgrow = Priority.NEVER
        }
    }
}

class UpdateWordsErrorScreen : View() {

    private val viewModel = find(UpdateWordsViewModel::class)

    override val root: Parent = vbox(alignment = Pos.CENTER) {
        label("Произошла ошибка! Попробуйте еще раз.") {
            addClass(AppStylesheet.h2)
            paddingBottom = SPACING_LARGE
        }
        hbox(alignment = Pos.CENTER) {
            val hasLocalWords = viewModel.hasLocalWords()
            button("Обновить") {
                HBoxConstraint(this, Insets(0.0, SPACING_LARGE.toDouble(), 0.0, 0.0))
                    .applyToNode(this)
                action {
                    this@UpdateWordsErrorScreen.replaceWith(UpdateWordsScreen::class)
                }
            }
            button("Выход") {
                isVisible = !hasLocalWords
                action {
                    Platform.exit()
                }
            }
            button("Не обновлять") {
                isVisible = hasLocalWords
                action {
                    this@UpdateWordsErrorScreen.replaceWith(MainScreen::class)
                }
            }
        }
    }
}