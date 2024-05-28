/*
 *abiola 2022
 */

package com.mshdabiola.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.ui.state.ExamType
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.Section
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel constructor(
    examOrdinal: Int,
    year: Long,
    val typeIndex: Int,
    private val iSubjectRepository: ISubjectRepository,
    private val settingRepository: ISettingRepository,
    private val questionRepository: IQuestionRepository,
    private val iExamRepository: IExaminationRepository,

) : ViewModel() {

    private var type: ExamType = ExamType.values()[examOrdinal]

    private val _mainState =
        MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            startExam(examType = type, year, typeIndex)
        }
    }

    private fun startExam(examType: ExamType, year: Long, typeIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = iExamRepository
                .getAll()
                .map {
                    it.map { it.toUi() }
                }
                .first()

            try {
                type = examType

                val exam = when (type) {
                    ExamType.YEAR -> {
                        list.single { it.year == year }
                    }

                    ExamType.FAST_FINGER -> {
                        list[0]
                    }

                    ExamType.RANDOM -> {
                        list.filter { it.isObjectiveOnly }.random()
                    }
                }

                val allQuestions = emptyList<List<QuestionUiState>>().toMutableList()
                when (type) {
                    ExamType.YEAR, ExamType.RANDOM -> {
                        val list = getAllQuestions(exam.id)
                        when (typeIndex) {
                            0 -> {
                                allQuestions.add(list.filter { it.isTheory.not() })
                                allQuestions.add(list.filter { it.isTheory })
                            }

                            1 -> {
                                allQuestions.add(list.filter { it.isTheory.not() })
                            }

                            else -> {
                                allQuestions.add(list.filter { it.isTheory })
                            }
                        }
                    }

                    ExamType.FAST_FINGER -> {
                        allQuestions.add(getAllQuestions(null).filter { it.isTheory.not() })
                    }
                }

                val section = allQuestions
                    .map { questionUiStates ->
                        val isTheory = questionUiStates.all { it.isTheory }
                        Section(stringRes = if (isTheory) 1 else 0, false)
                    }

                // Timber.e("time ${exam.examTime}")
                val time = when (type) {
                    ExamType.RANDOM, ExamType.YEAR -> exam.duration * 60L
                    ExamType.FAST_FINGER -> allQuestions[0].size * 30L
                }

//            val objTime = (allQuestions.filter { it.isTheory.not() }.size) * time
//            val theoryTime = (allQuestions.filter { it.isTheory }.size) * 40L
//            Timber.e("tTime $theoryTime objTime $objTime")
                val choose = allQuestions.map {
                    List(it.size) { -1 }
                }

                _mainState.update { state ->
                    state.copy(
                        currentExam = exam,
                        questions = allQuestions.map { it.toImmutableList() }.toImmutableList(),
                        choose = choose.map { it.toImmutableList() }.toImmutableList(),
                        currentSectionIndex = 0,
                        sections = section.toImmutableList(),
                        totalTime = time,
                        currentTime = 0,
                        //  examPart = typeIndex,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun onContinueExam() {
        val currentExam1 = settingRepository.currentExam.first()
        //   Timber.e("setting exam $currentExam1")

        if (currentExam1 != null) {
            val allQuestions = emptyList<List<QuestionUiState>>().toMutableList()

            val list = getAllQuestions(currentExam1.id)
            when (currentExam1.examPart) {
                0 -> {
                    allQuestions.add(list.filter { it.isTheory.not() })
                    allQuestions.add(list.filter { it.isTheory })
                }

                1 -> {
                    allQuestions.add(list.filter { it.isTheory.not() })
                }

                else -> {
                    allQuestions.add(list.filter { it.isTheory })
                }
            }
            val listexam = iExamRepository
                .getAll()
                .map {
                    it.map { it.toUi() }
                }
                .first()
            val exam = listexam.find {
                currentExam1.id == it.id
            }
            val chooses = currentExam1.choose.map { it.toImmutableList() }.toImmutableList()
            val section = allQuestions
                .mapIndexed { index, questionUiStates ->
                    val isTheory = questionUiStates.all { it.isTheory }
                    val isFinished = chooses[index].all { it > -1 }
                    Section(stringRes = if (isTheory) 1 else 0, isFinished)
                }

            _mainState.update { state ->
                state.copy(
                    currentExam = exam,
//                    examPart = currentExam1.examPart,
                    currentTime = currentExam1.currentTime,
                    totalTime = currentExam1.totalTime,
//                    isSubmit = currentExam1.isSubmit,
                    currentSectionIndex = currentExam1.paperIndex,
                    choose = chooses,
                    sections = section.toImmutableList(),
                    questions = allQuestions.map { it.toImmutableList() }.toImmutableList(),
                )
            }
        }
    }

    private suspend fun getAllQuestions(id: Long?): List<QuestionUiState> {
        val que = if (id == null) {
            questionRepository.getByRandom(6)
        } else {
            questionRepository
                .getByExamId(id)
        }

        return que
            .map { questionFulls ->
                questionFulls
                    .map {
                        it.copy(
                            options = it.options?.shuffled(),
                        )
                            .toQuestionUiState()
                            .copy(title = getTitle(it.examId, it.number, it.isTheory))
                    }
            }
            .firstOrNull() ?: emptyList()
    }

    fun onOption(sectionIndex: Int, questionIndex: Int, optionIndex: Int?) {
//
        val chooses = mainState.value.choose.toMutableList()
        val choose = chooses[sectionIndex].toMutableList()
        choose[questionIndex] = optionIndex ?: 2
        chooses[sectionIndex] = choose.toImmutableList()

        val isFinished = choose.all { it > -1 }
        val sections = mainState.value.sections.toMutableList()
        sections[sectionIndex] = sections[sectionIndex].copy(isFinished = isFinished)

        val currentSection = if (isFinished) {
            val newIndex = sectionIndex + 1
            if (sections.getOrNull(newIndex) == null) sectionIndex else newIndex
        } else {
            sectionIndex
        }

        _mainState.update {
            it.copy(
                choose = chooses.toImmutableList(),
                sections = sections.toImmutableList(),
                currentSectionIndex = currentSection,
            )
        }

        // add and remove Choose
    }

    private suspend fun saveCurrentExam() {
        val id = mainState.value

        if (id.currentExam != null && type.save) {
            settingRepository.setCurrentExam(
                CurrentExam(
                    id = id.currentExam!!.id,
                    currentTime = id.currentTime,
                    totalTime = id.totalTime,
//                    isSubmit = id.isSubmit,
                    examPart = typeIndex,
                    paperIndex = mainState.value.currentSectionIndex,
                    choose = mainState.value.choose,
                ),
            )
        }
    }

    private var saveJob: Job? = null
    fun onTimeChanged(time: Long) {
        _mainState.update {
            it.copy(currentTime = time)
        }
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            saveCurrentExam()
        }
    }

    private fun getTitle(examId: Long, no: Long, isTheory: Boolean): String {
        //  val exam = mainState.value.listOfAllExams.find { it.id == examId }

        return "Waec" // ${exam?.year} ${if (isTheory) "Theo" else "Obj"} Q$no"
    }

    fun changeIndex(index: Int) {
        _mainState.update {
            it.copy(currentSectionIndex = index)
        }
    }
}
