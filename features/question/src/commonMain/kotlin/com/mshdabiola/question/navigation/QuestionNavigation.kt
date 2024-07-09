/*
 *abiola 2022
 */

package com.mshdabiola.question.navigation

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.question.QuestionRoute
import com.mshdabiola.question.QuestionViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

const val ROUTE = "question_route"


@VisibleForTesting
internal const val QUESTION_ID_EXAM_TYPE = "examtype"
internal const val QUESTION_ID_YEAR = "year"
internal const val QUESTION_ID_INDEX = "index"

const val QUESTION_ROUTE = "$ROUTE/{$QUESTION_ID_EXAM_TYPE}/{$QUESTION_ID_YEAR}/{$QUESTION_ID_INDEX}"


fun NavController.navigateToQuestion(examType: Int, year: Long, typeIndex: Int) =
    navigate(
        route = "$ROUTE/$examType/$year/$typeIndex",
    )

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.questionScreen(
    modifier: Modifier = Modifier,

    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    navigateToFinish: (Int,Long,Int) -> Unit,

    ) {
    composable(
        route = QUESTION_ROUTE,
        arguments = listOf(
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
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
        },
        exitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
        },
    ) { backStack ->

        val year = backStack.arguments!!.getLong(QUESTION_ID_YEAR)
        val exam = backStack.arguments!!.getInt(QUESTION_ID_EXAM_TYPE)
        val index = backStack.arguments!!.getInt(QUESTION_ID_INDEX)

        val viewModel: QuestionViewModel = koinViewModel(
            parameters = {
                parametersOf(
                    exam,
                    year,
                    index,
                )
            },
        )

        QuestionRoute(
            modifier = modifier,
            onBack = onBack,
            onShowSnackbar = onShowSnack,
            navigateToFinish = { navigateToFinish(exam,year,index) },
            viewModel = viewModel,
        )
    }
}
