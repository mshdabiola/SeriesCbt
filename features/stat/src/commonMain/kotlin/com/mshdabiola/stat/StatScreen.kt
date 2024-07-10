/*
 *abiola 2022
 */

package com.mshdabiola.stat

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

// import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainRoute(
    modifier: Modifier = Modifier,

    onShowSnackbar: suspend (String, String?) -> Boolean,

) {
    val viewModel: StatViewModel = koinViewModel()

    StatScreen(
        modifier = modifier,
        statState = StatState(),
    )
}

@Composable
internal fun StatScreen(
    modifier: Modifier = Modifier,
    statState: StatState,
) {
    Column(modifier = modifier) {
        Text("Statics")
    }
}

// @Composable
// expect fun StatScreenPreview()
