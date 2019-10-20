package com.ncbs.dictionary.presentation

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
                viewModel.locales.forEach {
                    item(it.language.toUpperCase()).action {
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

    private val titleWord = viewModel.selectedWord.stringBinding { viewModel.getTranslateBySelectedLocale(it)}

    init {
        //todo
        viewModel.selectedLocale.onChange {
            titleWord.invalidate()
        }
    }

    override val root = hbox {
        text(titleWord)
        addClass(AppStylesheet.container)
    }
}