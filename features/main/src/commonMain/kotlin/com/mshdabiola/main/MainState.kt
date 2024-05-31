package com.mshdabiola.main

import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MainState(
    val currentExam: ExamUiState? = null,
    val listOfAllExams: ImmutableList<ExamUiState>,
    val choose: ImmutableList<ImmutableList<Int>> =
        listOf(emptyList<Int>().toImmutableList()).toImmutableList(),
    val examPart: Int = 0,
    val currentTime: Long = 0,
    val totalTime: Long = 4,
    val isSubmit: Boolean = false,
)
