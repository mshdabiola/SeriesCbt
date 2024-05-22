/*
 *abiola 2022
 */

package com.mshdabiola.finish.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mshdabiola.finish.FinishRoute
import com.mshdabiola.finish.FinishViewModel
import com.mshdabiola.ui.ScreenSize
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

const val FINISH_ROUTE ="finish_route"

fun NavController.navigateToFinish() = navigate(FINISH_ROUTE)

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.finishScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    navigateToQuestion: (Int, Long, Int) -> Unit ,
    screenSize: ScreenSize,
) {
    composable(route = FINISH_ROUTE) {
        val viewModel: FinishViewModel = koinViewModel()

        FinishRoute(
            screenSize = screenSize,
            onBack = onBack,
            onShowSnackbar = onShowSnack,
            navigateToQuestion = navigateToQuestion,
            viewModel = viewModel
        )
    }
}
