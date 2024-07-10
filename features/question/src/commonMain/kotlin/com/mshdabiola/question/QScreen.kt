package com.mshdabiola.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.theme.extendedColorScheme
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun QuestionRoute(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    navigateToFinish: () -> Unit,
    viewModel: QuestionViewModel,

) {
    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon()

    val states = getState(
        sizes = mainState
            .value
            .questions
            .map { it.size }
            .toImmutableList(),
    )

    QuestionScreen(
        modifier = modifier,
        mainStat = mainState.value,
        onFinish = {
            onBack()
            navigateToFinish()
        },
        onOption = viewModel::onOption,
        onTimeChanged = viewModel::onTimeChanged,
        changeIndex = viewModel::changeIndex,
        pagerState = states,
    )
//    InstructionBottomSheet(
//        instructionUiState = instructionUiState,
//        onDismissRequest = { instructionUiState = null },
//    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class,
)
@Composable
internal fun QuestionScreen(
    modifier: Modifier = Modifier,
    mainStat: MainState,
    pagerState: ImmutableList<PagerState>,
    onFinish: () -> Unit = {},
    onOption: (Int, Int, Int) -> Unit = { _, _, _ -> }, // paper,question,option
    onTimeChanged: (Long) -> Unit = {},
    changeIndex: (Int) -> Unit = {},
) {
    if (mainStat.questions.flatten().isEmpty()) {
        Text(text = "empty")
    } else {
        var instructionUiState by remember {
            mutableStateOf<InstructionUiState?>(null)
        }
        val coroutineScope = rememberCoroutineScope()

        val finishPercent = remember(mainStat.choose) {
            val allChoose = mainStat
                .choose
                .flatten()

            (
                (
                    allChoose.count {
                        it > -1
                    } / allChoose.size.toFloat()
                    ) * 100
                ).toInt()
        }

        LaunchedEffect(
            key1 = mainStat.currentTime,
            block = {
                mainStat.currentExam?.let {
                    if (mainStat.currentTime == mainStat.totalTime) {
                        onTimeChanged(mainStat.totalTime)
                        onFinish()
                    }
                }
            },
        )
        var isAllShowing by remember() {
            mutableStateOf(false)
        }

//        Scaffold(
//            modifier = Modifier,
//            bottomBar = {
//                BottomAppBar(
//                    actions = {
//                        IconButton(onClick = back, modifier = Modifier.testTag("question:back")) {
//                            Icon(
//                                imageVector = Icons.Default.ArrowBackIosNew,
//                                contentDescription = "back",
//                            )
//                        }
//                    },
//                    floatingActionButton = {
//                        ExtendedFloatingActionButton(
//                            modifier = Modifier.testTag("question:submit"),
//                            onClick = onFinish,
//                            containerColor = if (finishPercent == 100) {
//                                correct()
//                            } else {
//                                FloatingActionButtonDefaults.containerColor
//                            },
//                            contentColor = if (finishPercent == 100) {
//                                onCorrect()
//                            } else {
//                                contentColorFor(backgroundColor = FloatingActionButtonDefaults.containerColor)
//                            },
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Kitesurfing,
//                                contentDescription = "submit",
//                            )
//                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
//                            Text(text = "Submit: $finishPercent%")
//                        }
//                    },
//                )
//            },
//
//        ) { paddingValues ->
        Column(modifier.verticalScroll(rememberScrollState())) {
            FlowRow(modifier = modifier) {
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .width(600.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TimeCounter(
                        modifier = Modifier.padding(top = 4.dp),
                        currentTime2 = mainStat.currentTime,
                        total = mainStat.totalTime,
                        onTimeChanged = onTimeChanged,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        Modifier,
                        horizontalArrangement = Arrangement.spacedBy(
                            4.dp,
                            Alignment.CenterHorizontally,
                        ),
                    ) {
                        if (mainStat.questions.size > 1) {
                            mainStat.sections.forEachIndexed { index, section ->
                                ElevatedSuggestionChip(
                                    onClick = { changeIndex(index) },
                                    colors = if (section.isFinished) {
                                        SuggestionChipDefaults.elevatedSuggestionChipColors(
                                            containerColor = extendedColorScheme.right.colorContainer,
                                            labelColor = extendedColorScheme.right.onColorContainer,

                                        )
                                    } else {
                                        SuggestionChipDefaults.elevatedSuggestionChipColors()
                                    },
                                    label = {
                                        Text(com.mshdabiola.designsystem.string.sections[section.stringRes])
                                    },
                                )
                            }
                        }
                    }
                    // AnimatedContent(modifier = Modifier.fillMaxSize(), targetState = mainStat.currentPaper, label = "dd") { paperIndex ->
                    ExamPaper(
                        questions = mainStat.questions[mainStat.currentSectionIndex],
                        state = pagerState[mainStat.currentSectionIndex],
                        choose = mainStat.choose[mainStat.currentSectionIndex],
                        isAllShowing = isAllShowing,
                        onShowAllQuetions = {
                            isAllShowing = true
                        },
                        //  onNextTheory = {},//onNextTheory,
                        setInstructionUiState = { instructionUiState = it },
                        onOption = { quIndex, optinId ->
                            onOption(
                                mainStat.currentSectionIndex,
                                quIndex,
                                optinId,
                            )
                        },
                    )
                }
                Column(
                    Modifier.weight(0.3f), // .height(40.dp),
                    //  horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
                ) {
                    if (isAllShowing) {
                        TextButton(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { isAllShowing = false },
                        ) {
                            Text(text = "Hide All Questions")
                        }
                        Spacer(Modifier.height(4.dp))
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                4.dp,
                                Alignment.CenterHorizontally,
                            ),
                            verticalArrangement = Arrangement.spacedBy(
                                4.dp,
                                Alignment.CenterVertically,
                            ),
                        ) {
                            mainStat.choose[mainStat.currentSectionIndex].forEachIndexed { index, i ->
                                QuestionNumberButton(
                                    number = index,
                                    isChoose = i > -1,
                                    isCurrent = index == pagerState[mainStat.currentSectionIndex].currentPage,
                                    onClick = { // onChooseClick(it)
                                        coroutineScope.launch {
                                            pagerState[mainStat.currentSectionIndex].animateScrollToPage(
                                                index,
                                            )
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }

        // }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.ExamPaper(
    questions: ImmutableList<QuestionUiState>,
    state: PagerState,
    choose: ImmutableList<Int>,
    // onNextTheory: (Int) -> Unit = {},
    setInstructionUiState: (InstructionUiState?) -> Unit = {},
    onOption: (Int, Int) -> Unit = { _, _ -> },
    isAllShowing: Boolean,
    onShowAllQuetions: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val lazyListState = rememberLazyListState()
//
    LaunchedEffect(state.currentPage) {
        println("current index ${state.currentPage}")
        val value = if (state.currentPage == 0) 0 else state.currentPage - 1
        lazyListState.scrollToItem(value)
    }

    val number = remember(choose) { choose.size }
    val noAnswer = remember(choose) {
        derivedStateOf {
            choose.count { it > -1 }
        }
    }

    HorizontalPager(
        modifier = Modifier
            .height(300.dp)
            .verticalScroll(state = scrollState),
        state = state,
        verticalAlignment = Alignment.Top,
        userScrollEnabled = false,
    ) { index ->

        QuestionUi(
            number = (index + 1L),
            questionUiState = questions[index],

            onInstruction = {
                setInstructionUiState(questions[index].instructionUiState!!)
            },
            selectedOption = choose.getOrNull(index) ?: -1,
            onOptionClick = {
                onOption(index, it)
                if (state.canScrollForward) {
                    coroutineScope
                        .launch {
                            state.animateScrollToPage(index + 1)
                            scrollState.scrollTo(0)
                        }
                }
            },
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
    ) {
        IconButton(
            enabled = state.canScrollBackward,
            onClick = {
                coroutineScope.launch {
                    state.animateScrollToPage(state.currentPage - 1)
                    scrollState.scrollTo(0)
                }
            },
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "prev")
        }

        LazyRow(
            state = lazyListState,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        ) {
            items(count = number, key = { it }) {
                if (!isAllShowing) {
                    QuestionNumberButton(
                        number = it,
                        isChoose = choose[it] > -1,
                        isCurrent = it == state.currentPage,
                    ) {
                        //  onNextTheory(it)
                        coroutineScope.launch {
                            state.animateScrollToPage(it)
                            scrollState.scrollTo(0)
                        }
                    }
                }
            }
        }
        IconButton(
            enabled = state.canScrollForward,
            onClick = {
                coroutineScope.launch {
                    // onNextTheory(state.currentPage + 1)
                    state.animateScrollToPage(state.currentPage + 1)
                    scrollState.scrollTo(0)
                }
            },
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "next")
        }
    }

    if (!isAllShowing) {
        TextButton(onClick = onShowAllQuetions) {
            Text("Show all questions")
        }
    }

    Text("${noAnswer.value} of $number")
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
//
// @Composable
// expect fun QuestionScreenPreview()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionNumberButton(
    number: Int,
    isChoose: Boolean,
    isCurrent: Boolean = false,
    onClick: () -> Unit = {},
) {
    val color = if (isChoose) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val border = isCurrent || !isChoose

    OutlinedCard(
        modifier = Modifier.requiredSize(48.dp),
        shape = CircleShape,
        colors = CardDefaults.outlinedCardColors(containerColor = color),
        border = CardDefaults.outlinedCardBorder(border),
        onClick = onClick,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("${number + 1}")
        }
    }
}
