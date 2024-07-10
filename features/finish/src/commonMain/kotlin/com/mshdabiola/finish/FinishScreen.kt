/*
 *abiola 2022
 */

package com.mshdabiola.finish

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.string.sections
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FinishRoute(
    modifier: Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: FinishViewModel,
) {
    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon()

    FinishScreen(
        modifier = modifier,
        mainState = mainState.value,
//        back = onBack,
        changeIndex = {},
//        navigateToQuestion = { e, y, t ->
//            onBack()
//            navigateToQuestion(e, y, t)
//        },
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun FinishScreen(
    modifier: Modifier,
    mainState: MainState,
    changeIndex: (Int) -> Unit = {},
) {
    val lazyState = rememberLazyListState()
    val currentIndex = mainState.currentSectionIndex
    val coroutineScope = rememberCoroutineScope()
//    var showAnswer by remember {
//        mutableStateOf(false)
//    }
    val pagerState = getState(
        sizes = mainState
            .questions
            .map { it.size }
            .toImmutableList(),
    )

    var state by remember { mutableStateOf(0) }

    LaunchedEffect(state) {
        delay(3000)
        val state2 = pagerState[mainState.currentSectionIndex]
        state2.animateScrollToPage(state)
        state = if (state2.canScrollForward) {
            state2.currentPage + 1
        } else {
            0
        }
    }

//    Scaffold(
//        modifier = Modifier,
//        bottomBar = {
//            BottomAppBar(
//                actions = {
//                    IconButton(onClick = back, modifier = Modifier.testTag("finish:back")) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBackIosNew,
//                            contentDescription = "back",
//                        )
//                    }
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
//                    }
//                },
//                floatingActionButton = {
//                    ExtendedFloatingActionButton(
//                        modifier = Modifier.testTag("finish:retry"),
//                        onClick = {
//                            navigateToQuestion(
//                                ExamType.YEAR.ordinal,
//                                mainState.examination.year,
//                                mainState.typeIndex,
//                            )
//                        },
//                    ) {
//                        Icon(imageVector = Icons.Default.Replay, contentDescription = "Share")
//                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
//                        Text(text = "Retry Questions")
//                    }
//                },
//            )
//        },
//    ) { paddingValues ->

    Column(modifier.verticalScroll(rememberScrollState())) {
        FlowRow(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Good Job",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                )

                FinishCard(
                    imageVector = Icons.Default.WineBar,
                    grade = mainState.score?.grade ?: 'B',
                )

                if (mainState.score != null) {
                    ScoreCard(mainState.score)
                }
            }
            Column(modifier = Modifier.weight(0.7f).width(600.dp)) {
                if (mainState.sections.size > 1) {
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

                HorizontalPager(
                    modifier = Modifier,
                    // .weight(0.8f)
                    // .verticalScroll(state = scrollState),
                    state = pagerState[mainState.currentSectionIndex],
                    verticalAlignment = Alignment.Top,
                    userScrollEnabled = false,
                ) { index ->

                    QuestionUi(
                        number = (index + 1L),
                        questionUiState = mainState.questions.get(
                            mainState.currentSectionIndex,
                        )[index],

                        selectedOption = mainState.choose[mainState.currentSectionIndex].getOrNull(
                            index,
                        ) ?: -1,
                        onOptionClick = {
                        },
                        showAnswer = true,
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState[mainState.currentSectionIndex]
                                    .animateScrollToPage(pagerState[mainState.currentSectionIndex].currentPage - 1)
                                state = pagerState[mainState.currentSectionIndex].currentPage
                            }
                        },
                        enabled = pagerState[mainState.currentSectionIndex].canScrollBackward,
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "previous")
                    }

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState[mainState.currentSectionIndex]
                                    .animateScrollToPage(pagerState[mainState.currentSectionIndex].currentPage + 1)

                                state = pagerState[mainState.currentSectionIndex].currentPage
                            }
                        },
                        enabled = pagerState[mainState.currentSectionIndex].canScrollForward,
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "previous")
                    }
                }
            }
        }
    }

    // }
//    InstructionBottomSheet(
//        instructionUiState = instructionUiState,
//
//        onDismissRequest = { instructionUiState = null },
//    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun getState(sizes: ImmutableList<Int>): ImmutableList<PagerState> {
    return sizes.map {
        rememberPagerState {
            it
        }
    }.toImmutableList()
}
// @Composable
// expect fun FinishScreenPreview()
