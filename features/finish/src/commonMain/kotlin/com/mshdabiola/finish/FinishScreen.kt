/*
 *abiola 2022
 */

package com.mshdabiola.finish

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.string.sections
import com.mshdabiola.ui.InstructionBottomSheet
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.semanticsCommon
import com.mshdabiola.ui.state.ExamType
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FinishRoute(
    screenSize: ScreenSize,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    navigateToQuestion: (Int, Long, Int) -> Unit ,
    viewModel: FinishViewModel
) {
    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon()

    FinishScreen(
        mainState = mainState.value,
        back =onBack,
        changeIndex = {},
        navigateToQuestion ={e,y,t->
            onBack()
            navigateToQuestion(e,y,t)
        },
    )
}

@Composable
internal fun FinishScreen(
    mainState: MainState,
    back: () -> Unit = {},
    navigateToQuestion: (Int, Long, Int) -> Unit = { _, _, _ -> },
    changeIndex: (Int) -> Unit = {},
) {
    val lazyState = rememberLazyListState()
    val currentIndex = mainState.currentSectionIndex
    val coroutineScope = rememberCoroutineScope()
    var showAnswer by remember {
        mutableStateOf(false)
    }
    var instructionUiState by remember {
        mutableStateOf<InstructionUiState?>(null)
    }

    Scaffold(
        modifier = Modifier.semanticsCommon {},
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = back) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "back",
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(onClick = { navigateToQuestion(ExamType.YEAR.ordinal, mainState.examination.year, mainState.typeIndex) }) {
                        Icon(imageVector = Icons.Default.Replay, contentDescription = "Share")
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Retry Questions")
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            state = lazyState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Text(
                    text = "Good Job",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            item {
                FinishCard(
                    imageVector = Icons.Default.WineBar,
                    grade = mainState.score?.grade ?: 'B',
                    isHide = !showAnswer,
                    onShowAnswers = {
                        showAnswer = !showAnswer
                        if (showAnswer) {
                            coroutineScope.launch {
                                lazyState.scrollToItem(3)
                            }
                        }
                    },
                )
            }
            item {
                if (mainState.score != null) {
                    ScoreCard(mainState.score!!)
                }
            }

            if (showAnswer) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        text = "Questions and answers",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.titleSmall,
//                        modifier = Modifier.fillMaxWidth()
//                    )
                }
                if (mainState.questions.size > 1) {
                    item {
                        TabRow(selectedTabIndex = currentIndex) {
                            mainState.sections.forEachIndexed { index, section ->
                                Tab(
                                    selected = currentIndex == index,
                                    onClick = {
                                        changeIndex(index)
                                        coroutineScope.launch {
                                            lazyState.scrollToItem(3)
                                        }
                                    },
                                    text = { Text(text = sections[section.stringRes]) },
                                )
                            }
                        }
                    }
                }
                itemsIndexed(
                    items = mainState.questions.getOrElse(
                        mainState.currentSectionIndex,
                    ) { emptyList<QuestionUiState>().toImmutableList() },
                    key = { _, item -> item.id },
                ) { index, item ->
                    QuestionUi(
                        number = (index + 1L),
                        questionUiState = item,
                        onInstruction = {
                            instructionUiState = item.instructionUiState
                        },
                        selectedOption = mainState.choose[mainState.currentSectionIndex].getOrNull(
                            index,
                        ) ?: -1,
                        onOptionClick = {
                        },
                        showAnswer = true,
                    )
                }
            }
        }
    }
    InstructionBottomSheet(
        instructionUiState = instructionUiState,

        onDismissRequest = { instructionUiState = null },
    )
}

//@Composable
//expect fun FinishScreenPreview()
