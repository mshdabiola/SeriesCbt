/*
 *abiola 2024
 */

package com.mshdabiola.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.CbtBackground
import com.mshdabiola.designsystem.component.CbtButton
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.designsystem.theme.CbtTheme

@ThemePreviews
@Composable
fun ButtonPreview() {
    CbtTheme {
        CbtBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            CbtButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonPreview2() {
    CbtTheme {
        CbtBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            CbtButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonLeadingIconPreview() {
    CbtTheme {
        CbtBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            CbtButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = SkIcons.Add, contentDescription = null) },
            )
        }
    }
}
