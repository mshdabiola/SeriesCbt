package com.mshdabiola.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.DetailTopAppBar
import com.mshdabiola.designsystem.component.CbtTopAppBar
import com.mshdabiola.designsystem.icon.CbtIcons
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Preview("Top App Bar")
@Composable
private fun SkTopAppBarPreview() {
    CbtTopAppBar(
        titleRes = "Testing",
        navigationIcon = CbtIcons.Search,
        navigationIconContentDescription = "Navigation icon",
        actionIcon = CbtIcons.MoreVert,
        actionIconContentDescription = "Action icon",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun DetailTopAppBarPreview() {
    DetailTopAppBar()
}
