package com.ncbs.dictionary.presentation

import javafx.geometry.Insets
import javafx.geometry.Pos
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
            button("Начать") {
                padding = Insets(10.0, 16.0, 10.0, 16.0)
                action {
                    this@OnBoardingScreen.replaceWith(
                        MainScreen::class
                    )
                }
            }
        }
    }
}