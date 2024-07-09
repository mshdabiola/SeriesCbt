/*
 *abiola 2022
 */

package com.mshdabiola.main.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.main.MainRoute
import com.mshdabiola.ui.ScreenSize

val MAIN_ROUTE = cbtRoute[0]

fun NavController.navigateToMain(navOptions: NavOptions) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen(
    modifier: Modifier = Modifier,

    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToQuestion: (Int, Long, Int) -> Unit = { _, _, _ -> },
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
            navigateToQuestion = { type, year, objType ->
                navigateToQuestion(type.ordinal, year, objType)
            },
        )
    }
}
