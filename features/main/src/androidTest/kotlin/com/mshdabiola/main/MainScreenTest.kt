/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_showsLoadingSpinner() {
//        composeTestRule.setContent {
//        }
//
//        composeTestRule
//            .onNodeWithContentDescription(
//                composeTestRule.activity.resources.getString(R.string.feature_bookmarks_loading),
//            )
//            .assertExists()
    }
}
