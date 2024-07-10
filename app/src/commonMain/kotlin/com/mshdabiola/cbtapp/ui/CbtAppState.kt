/*
 *abiola 2022
 */

package com.mshdabiola.cbtapp.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.finish.navigation.FINISH_ROUTE
import com.mshdabiola.finish.navigation.QUESTION_ID_EXAM_TYPE
import com.mshdabiola.finish.navigation.QUESTION_ID_INDEX
import com.mshdabiola.finish.navigation.QUESTION_ID_YEAR
import com.mshdabiola.finish.navigation.navigateToFinish
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.profile.navigation.PROFILE_ROUTE
import com.mshdabiola.profile.navigation.navigateToProfile
import com.mshdabiola.question.navigation.QUESTION_ROUTE
import com.mshdabiola.question.navigation.navigateToQuestion
import com.mshdabiola.setting.navigation.SETTING_ROUTE
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.stat.navigation.STAT_ROUTE
import com.mshdabiola.stat.navigation.navigateToStat
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberCbtAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): CbtAppState {
    // NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        CbtAppState(
            navController,
            coroutineScope,
            windowSizeClass,
        )
    }
}

@Stable
class CbtAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val showTopBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    val fabName: String
        @Composable get() = when {
            currentDestination?.route == QUESTION_ROUTE -> "Finish"
            currentDestination?.route == FINISH_ROUTE -> "Retry"
            else -> "Fab"
        }

    val isMain: Boolean
        @Composable get() =
            cbtRoute.contains(currentDestination?.route)
    val isQuestion: Boolean
        @Composable get() =
            currentDestination?.route == QUESTION_ROUTE

    val showFab: Boolean
        @Composable get() = when {
            currentDestination?.route == QUESTION_ROUTE || currentDestination?.route == FINISH_ROUTE -> true
            else -> false
        }

    fun onFabClick() {
        when (navController.currentDestination?.route) {
            QUESTION_ROUTE -> {
                val year = navController.currentBackStackEntry?.arguments!!.getLong(QUESTION_ID_YEAR)
                val exam = navController.currentBackStackEntry?.arguments!!.getInt(QUESTION_ID_EXAM_TYPE)
                val index = navController.currentBackStackEntry?.arguments!!.getInt(QUESTION_ID_INDEX)

                println("year $year exam $exam index $index")

                //                navController.currentBackStackEntry
                navController.popBackStack()
                navController.navigateToFinish(exam, year, index)
            }
            FINISH_ROUTE -> {
                val year = navController.currentBackStackEntry?.arguments!!.getLong(QUESTION_ID_YEAR)
                val exam = navController.currentBackStackEntry?.arguments!!.getInt(QUESTION_ID_EXAM_TYPE)
                val index = navController.currentBackStackEntry?.arguments!!.getInt(QUESTION_ID_INDEX)

                println("year $year exam $exam index $index")

                navController.popBackStack()
                navController.navigateToQuestion(exam, year, index)
            }
            else -> {}
        }
    }

    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
            cbtRoute.contains(currentDestination?.route)

    val shouldShowGeneralBottomBar: Boolean
        @Composable get() = windowSizeClass.widthSizeClass < WindowWidthSizeClass.Expanded &&
            !cbtRoute.contains(currentDestination?.route)

    val shouldShowNavRail: Boolean
        @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium &&
            cbtRoute.contains(currentDestination?.route)

    val shouldShowDrawer: Boolean
        @Composable get() =
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
                cbtRoute.contains(currentDestination?.route)

    fun onNavigate(route: String) {
        when (route) {
            MAIN_ROUTE -> {
                navController.navigateToMain(
                    navOptions = navOptions {
                        launchSingleTop
                        this.restoreState
                    },
                )
            }

            SETTING_ROUTE -> {
                navController.navigateToSetting()
            }

            PROFILE_ROUTE -> {
                navController.navigateToProfile(
                    navOptions {
                        launchSingleTop
                        restoreState
                    },
                )
            }

            STAT_ROUTE -> {
                navController.navigateToStat(
                    navOptions {
                        launchSingleTop
                        restoreState
                    },
                )
            }
        }
    }
}
//
// @Composable
// private fun NavigationTrackingSideEffect(navController: NavHostController) {
//    TrackDisposableJank(navController) { metricsHolder ->
//        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
//            metricsHolder.state?.putState("Navigation", destination.route.toString())
//        }
//
//        navController.addOnDestinationChangedListener(listener)
//
//        onDispose {
//            navController.removeOnDestinationChangedListener(listener)
//        }
//    }
// }
