/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.layer1
import com.mshdabiola.designsystem.drawable.layer2
import com.mshdabiola.designsystem.drawable.layer3
import com.mshdabiola.designsystem.string.examPart
import com.mshdabiola.designsystem.string.subject
import com.mshdabiola.designsystem.string.type
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.semanticsCommon
import com.mshdabiola.ui.state.ExamType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
internal fun MainRoute(
    screenSize: ScreenSize,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSetting: () -> Unit,
    navigateToQuestion: (ExamType, Long, Int) -> Unit,
) {
    val viewModel: MainViewModel = koinViewModel()

    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon()
    MainScreen(
        mainState = mainState.value,
        onSetting = navigateToSetting,
        navigateToQuestion = navigateToQuestion,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    mainState: MainState,
    navigateToQuestion: (ExamType, Long, Int) -> Unit = { _, _, _ -> },
    onSetting: () -> Unit = {},
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val finishPercent = remember(mainState.choose) {
        var choose = mainState
            .choose
            .flatten()
        choose.count {
            it > -1
        } / choose.size.toFloat()
    }

    val state = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .semanticsCommon {},
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = subject)
                        Text(
                            text = type,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = onSetting) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                    }
                },

            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(state)
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    PlayLogin()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Step right up and test your skills. " +
                            "Wellcome to Physics test that will challenge and entertain you",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center,

                    )
                }
                Image(painter = layer2, contentDescription = "")
            }
            mainState.currentExam?.let {
                ContinueCard(
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

            StartCard(
                exams = mainState.listOfAllExams,
                isSubmit = mainState.isSubmit,
                onClick = { objIndex, year ->
                    navigateToQuestion(ExamType.YEAR, year, objIndex)
                },
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                OtherCard(
                    modifier = Modifier.testTag("main:random"),
                    title = "Random exam",
                    painter = layer1,
                    onClick = {
                        navigateToQuestion(ExamType.RANDOM, -1, 1)
                    },
                )
                OtherCard(
                    modifier = Modifier.testTag("main:fast"),
                    title = "Fast finger",
                    painter = layer3,
                    onClick = {
                        navigateToQuestion(ExamType.FAST_FINGER, -1, 1)
                    },
                )
            }
        }
    }
}
//
// @Composable
// expect fun MainScreenPreview()
