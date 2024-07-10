/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.layer2
import com.mshdabiola.designsystem.string.examPart
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.state.ExamType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
internal fun MainRoute(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToQuestion: (ExamType, Long, Int) -> Unit,
) {
    val viewModel: MainViewModel = koinViewModel()

    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon()
    MainScreen(
        modifier = modifier,
        mainState = mainState.value,
        navigateToQuestion = navigateToQuestion,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    mainState: MainState,
    navigateToQuestion: (ExamType, Long, Int) -> Unit = { _, _, _ -> },
) {
    val finishPercent = remember(mainState.choose) {
        var choose = mainState
            .choose
            .flatten()
        choose.count {
            it > -1
        } / choose.size.toFloat()
    }

    val state = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FlowRow(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
//                maxItemsInEachRow = 2,
        ) {
            FlowRow(
                Modifier
                    .weight(0.3f),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                Column(
                    Modifier.weight(1f).width(200.dp).heightIn(120.dp, 200.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    PlayLogin()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Step right up and test your skills. " +
                            "Wellcome to Physics test that will challenge and entertain you",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,

                    )
                }
                Image(painter = layer2, contentDescription = "")
            }

            Column(
                Modifier
                    .width(600.dp)
                    .weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterVertically,
                ),
                // horizontalAlignment = Alignment.CenterHorizontally,

//                maxItemsInEachRow = 2,
            ) {
                mainState.currentExam?.let {
                    ContinueCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        year = it.year,
                        progress = finishPercent,
                        enabled = mainState.isSubmit.not(),
                        timeRemain = mainState.totalTime - mainState.currentTime,
                        part = examPart[mainState.examPart],
                        onClick = {
                            navigateToQuestion(ExamType.YEAR, it.year, 1)
                        },
                    )
                }

                Column(
                    Modifier
                        .fillMaxWidth(),
                ) {
                    StartCard(
                        exams = mainState.listOfAllExams,
                        isSubmit = mainState.isSubmit,
                        onClick = { objIndex, year ->
                            navigateToQuestion(ExamType.YEAR, year, objIndex)
                        },
                        onFast = { navigateToQuestion(ExamType.FAST_FINGER, -1, 1) },
                        onRandom = { navigateToQuestion(ExamType.RANDOM, -1, 1) },
                    )
                }
            }
        }
    }
}
//
// @Composable
// expect fun MainScreenPreview()
