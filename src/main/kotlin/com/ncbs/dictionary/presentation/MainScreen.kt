package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.domain.Language
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.Priority
import tornadofx.*

class MainScreen : View() {

    private val listView = find(WordsListView::class)
    private val detailView = find(WordDetailView::class)
    private val menuBarView = find(MenuBarView::class)

    init {
        whenDocked {
            primaryStage.width = 1024.0
            primaryStage.height = 768.0
            primaryStage.isResizable = true
        }
    }

    override val root = vbox {
        borderpane {
            vgrow = Priority.ALWAYS
            top = menuBarView.root
            left = listView.root
            center = detailView.root
        }
    }
}

class MenuBarView : View() {

    private val viewModel = find(DictionaryViewModel::class)

    private val toggleGroup = ToggleGroup()

    override val root = menubar {
        menu("Файл") {
            item("Выход").action { close() }
        }
        menu("Язык") {
            Language.values().forEach {
                radiomenuitem(it.title, toggleGroup) {
                    isSelected = viewModel.selectedLocale.value == it
                    action {
                        viewModel.selectedLocale.value = it
                    }
                }
            }
        }
    }
}

class WordsListView : View() {

    private val viewModel = find(DictionaryViewModel::class)

    private val search = textfield() {
        promptText = "Поиск"
        textProperty().addListener { _, _, newValue ->
            viewModel.onSearchQueryChanged(newValue)
        }
    }

    private val list = listview(viewModel.filteredWords) {
        vgrow = Priority.ALWAYS
        cellFormat {
            setText(viewModel.getTranslateBySelectedLocale(it) ?: return@cellFormat)
        }
        setOnMouseClicked {
            selectedItem ?: return@setOnMouseClicked
            viewModel.selectedWord.value = selectedItem
        }
    }

    override val root = vbox()

    init {
        with(root) {
            this += search
            this += list
        }
        viewModel.selectedLocale.onChange {
            list.refresh()
        }
    }
}

class WordDetailView : View() {
    private val viewModel = find(DictionaryViewModel::class)

    private val titleWord = viewModel.selectedWord.stringBinding { it?.getTranslate(Language.NIVKH.code) }
    private val russianTranslate = viewModel.selectedWord.stringBinding { it?.getTranslate(Language.RUSSIAN.code) }
    private val englishTranslate = viewModel.selectedWord.stringBinding { it?.getTranslate(Language.ENGLISH.code) }

    override val root = vbox {
        hbox(SPACING_MEDIUM) {
            label {
                textProperty().bind(titleWord)
                addClass(AppStylesheet.h1)
            }
            button {
                HBoxConstraint(this, Insets(18.0, 0.0, 0.0, 0.0))
                    .applyToNode(this)
                visibleWhen(viewModel.isPlayButtonVisible)
                action {
                    viewModel.onPlay()
                }
                graphic = imageview("/ic_play.png") {
                    fitHeight = SPACING_MEDIUM.toDouble()
                    fitWidth = SPACING_MEDIUM.toDouble()
                }
            }
            alignment = Pos.TOP_LEFT
        }
        vbox {
            label("RU") {
                paddingTop = SPACING_LARGE
                addClass(AppStylesheet.subtitle)
            }
            label {
                textProperty().bind(russianTranslate)
                addClass(AppStylesheet.content)
            }
            label("EN") {
                paddingTop = SPACING_MEDIUM
                addClass(AppStylesheet.subtitle)
            }
            label {
                textProperty().bind(englishTranslate)
                addClass(AppStylesheet.content)
            }
        }
        style {
            padding = box(72.px)
        }
    }
}