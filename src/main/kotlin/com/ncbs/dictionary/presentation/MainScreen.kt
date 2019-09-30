package com.ncbs.dictionary.presentation

import com.ncbs.dictionary.data.WordsDataSourceImpl
import tornadofx.*
import javax.swing.text.View

class MainScreen : View() {

    private val leftView = find(WordsListView::class)
    private val topView = find(SearchView::class)
    private val centerView = find(WordDetailView::class)

    override val root = vbox {
        menubar {
            menu("Файл") {
                item("Выход").action { close() }
            }
            menu("Язык") {
                item("RU", "Русский")
                item("EN", "Английский")
                item("NV", "Нивхский")
            }
        }

        borderpane {
            left = leftView.root
            top = topView.root
            center = centerView.root
        }
    }
}

class WordsListView: View() {

    private val words = WordsDataSourceImpl().getWords().map { it.locales.first().value }.observable()

    override val root = listview(words) {
    }
}

class SearchView: View() {
    override val root = hbox {
        textfield{
            promptText = "Поиск"
        }
        addClass(AppStylesheet.container)
    }
}

class WordDetailView: View() {
    override val root = hbox {
        label("WordDetailView")
        addClass(AppStylesheet.container)
    }
}