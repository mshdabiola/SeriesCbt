/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SkTab
import com.mshdabiola.designsystem.component.SkTabRow
import com.mshdabiola.designsystem.theme.CbtTheme

@ThemePreviews
@Composable
fun TabsPreview() {
    CbtTheme {
        val titles = listOf("Topics", "People")
        SkTabRow(selectedTabIndex = 0) {
            titles.forEachIndexed { index, title ->
                SkTab(
                    selected = index == 0,
                    onClick = { },
                    text = { Text(text = title) },
                )
            }
        }
    }
}
