/*
 *abiola 2022
 */

package com.mshdabiola.profile.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.profile.ProfileRoute

val PROFILE_ROUTE = cbtRoute[1]

fun NavController.navigateToProfile(navOptions: NavOptions) = navigate(PROFILE_ROUTE, navOptions)

fun NavGraphBuilder.profileScreen(
    modifier: Modifier = Modifier,

    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(route = PROFILE_ROUTE) {
        ProfileRoute(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
        )
    }
}
