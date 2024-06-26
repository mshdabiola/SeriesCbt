/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.CbtBackground
import com.mshdabiola.designsystem.component.CbtGradientBackground
import com.mshdabiola.testing.util.captureMultiTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class BackgroundScreenshotTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun niaBackground_multipleThemes() {
        composeTestRule.captureMultiTheme("Background") { description ->
            CbtBackground(Modifier.size(100.dp)) {
                Text("$description background")
            }
        }
    }

    @Test
    fun niaGradientBackground_multipleThemes() {
        composeTestRule.captureMultiTheme("Background", "GradientBackground") { description ->
            CbtGradientBackground(Modifier.size(100.dp)) {
                Text("$description background")
            }
        }
    }
}
