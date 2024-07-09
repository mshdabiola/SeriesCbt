/*
 *abiola 2022
 */

package com.mshdabiola.stat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.MoreRankButton
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.UserRankUiState
import com.mshdabiola.ui.state.UserRank
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

// import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainRoute(
    modifier: Modifier=Modifier,

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
    modifier: Modifier=Modifier,
    statState: StatState,
) {
    Column (modifier = modifier){
        Text("Statics")

    }
}

// @Composable
// expect fun StatScreenPreview()
