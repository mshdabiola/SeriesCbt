/*
 *abiola 2022
 */

package com.mshdabiola.finish.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.finish.FinishRoute
import com.mshdabiola.finish.FinishViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

const val ROUTE = "finish_route"

const val QUESTION_ID_EXAM_TYPE = "examtype"
const val QUESTION_ID_YEAR = "year"
const val QUESTION_ID_INDEX = "index"

const val FINISH_ROUTE = "$ROUTE/{$QUESTION_ID_EXAM_TYPE}/{$QUESTION_ID_YEAR}/{$QUESTION_ID_INDEX}"

fun NavController.navigateToFinish(examType: Int, year: Long, typeIndex: Int) =
    navigate(
        route = "$ROUTE/$examType/$year/$typeIndex",
    )

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.finishScreen(
    modifier: Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,

) {
    composable(
        route = FINISH_ROUTE,
        listOf(
            navArgument(QUESTION_ID_EXAM_TYPE) {
                type = NavType.IntType
            },
            navArgument(QUESTION_ID_YEAR) {
                type = NavType.LongType
            },
            navArgument(QUESTION_ID_INDEX) {
                type = NavType.IntType
            },
        ),
    ) {
        val viewModel: FinishViewModel = koinViewModel()

        FinishRoute(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
            viewModel = viewModel,
        )
    }
}
