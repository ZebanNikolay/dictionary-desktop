package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.domain.Language
import tornadofx.*

class MainScreen : View() {

    private val leftView = find(WordsListView::class)
    private val topView = find(SearchView::class)
    private val centerView = find(WordDetailView::class)

    private val viewModel = find(DictionaryViewModel::class)

    override val root = vbox {
        menubar {
            menu("Файл") {
                item("Выход").action { close() }
            }
            menu("Язык") {
                Language.values().forEach {
                    item(it.title).action {
                        viewModel.selectedLocale.value = it
                    }
                }
            }
        }
        borderpane {
            left = leftView.root
            top = topView.root
            center = centerView.root
        }
    }
}

class WordsListView : View() {

    private val viewModel = find(DictionaryViewModel::class)

    init {
        viewModel.selectedLocale.onChange {
            root.refresh()
        }
    }

    override val root = listview(viewModel.filteredWords) {
        cellFormat {
            setText(viewModel.getTranslateBySelectedLocale(it) ?: return@cellFormat)
        }
        setOnMouseClicked {
            selectedItem ?: return@setOnMouseClicked
            viewModel.selectedWord.value = selectedItem
        }
    }
}

class SearchView : View() {

    private val viewModel = find(DictionaryViewModel::class)

    override val root = hbox {
        textfield() {
            promptText = "Поиск"
            textProperty().addListener { _, _, newValue ->
                viewModel.onSearchQueryChanged(newValue)
            }
        }
        addClass(AppStylesheet.container)
    }
}

class WordDetailView : View() {
    private val viewModel = find(DictionaryViewModel::class)

    private val titleWord = viewModel.selectedWord.stringBinding { it?.getTranslate(Language.NIVKH.code) }
    private val russianTranslate = viewModel.selectedWord.stringBinding { it?.getTranslate(Language.RUSSIAN.code) }
    private val englishTranslate = viewModel.selectedWord.stringBinding { it?.getTranslate(Language.ENGLISH.code) }

    override val root = vbox {
        hbox {
            text(titleWord)
            button("►")
        }
        vbox {
            text(russianTranslate)
            text(englishTranslate)
        }
        addClass(AppStylesheet.container)
    }
}