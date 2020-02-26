package com.ncbs.dictionary.presentation

import javafx.geometry.Pos
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TabPane
import javafx.scene.layout.Background
import tornadofx.*

class OnBoardingScreen : View() {

    private val backgroundImage = resources.image("/onboarding_background.jpg")
    private val aboutImage = resources.image("/about_text.jpg")
    private val onboardingImage = resources.image("/onboarding_text.jpg")

    init {
        whenDocked {
            primaryStage.height = backgroundImage.height + (primaryStage.height - primaryStage.scene.height)
            primaryStage.isResizable = false
            primaryStage.icons.add(resources.image("/ic_launch.png"))
        }
    }

    override val root = borderpane {
        background = Background.EMPTY
        left = imageview(backgroundImage)
        right = vbox(alignment = Pos.TOP_CENTER) {
            minWidth = aboutImage.width
            tabpane {
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                tab("Предисловие") {
                    imageview(aboutImage)
                }
                tab("Как пользоваться") {
                    imageview(onboardingImage)
                }
            }
            button("Аудиословать") {
                action {
                    this@OnBoardingScreen.replaceWith(
                        MainScreen::class
                    )
                }
                graphic = imageview("/ornament.png") {
                    fitHeight = 24.toDouble()
                    fitWidth = 120.toDouble()
                }
                style {
                    fontSize = 18.px
                    backgroundRadius = multi(box(24.px))
                    padding = box(10.px, 16.px)
                    contentDisplay = ContentDisplay.BOTTOM
                }
                addClass(AppStylesheet.onBoardingButtonHover)
            }
        }
    }
}