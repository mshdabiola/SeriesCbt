/*
 *abiola 2022
 */

package com.mshdabiola.stat.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.stat.MainRoute
import com.mshdabiola.ui.ScreenSize

val STAT_ROUTE = cbtRoute[2]

fun NavController.navigateToStat(navOptions: NavOptions) = navigate(STAT_ROUTE, navOptions)

fun NavGraphBuilder.statScreen(
    modifier: Modifier = Modifier,

    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(route = STAT_ROUTE) {
        MainRoute(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
        )
    }
}
