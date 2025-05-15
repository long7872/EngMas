package com.example.engmas.ui.screens.practice.courses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.courses.data.QuestionStatus

object PracticeCourseDestination: NavigationDestination {
    override val route = "practice/course"
    override val titleRes = R.string.tab_course
    const val ITEM_ARGS = "course_id"
    val routeWithArgs = "$route/{$ITEM_ARGS}"
}

@Composable
fun CourseProcessing(
    courseId: Int,
    onBackClicked: () -> Unit,
    viewModel: CourseViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getQuestionInCourses(courseId)
    }

    val screenState = uiState.screenState

    when (screenState) {
        CourseScreenState.List -> {
            CourseScreen(
                courseName = uiState.selectedCourse.name,
                listVocab = uiState.vocabQuestions,
                listGrammar = uiState.grammarQuestions,
                onVocabClicked = {
                    viewModel.setSelectedVocab(it)
                    viewModel.setupGame()
                    viewModel.changeScreenState(CourseScreenState.Vocabulary)
                },
                onGrammarClicked = {
                    viewModel.setSelectedGrammar(it)
                    viewModel.changeScreenState(CourseScreenState.Grammar)
                },
                onMarkAllClicked = {
                    viewModel.updateStatusForAllItems(QuestionStatus.Known)
                },
                onStartLearningClicked = {
                    val isVocabListHasLearningStatus = viewModel.checkVocabHasLearningStatus()
                    val isGrammarListHasLearningStatus = viewModel.checkGrammarHasLearningStatus()
                    if (isVocabListHasLearningStatus) {
                        viewModel.setSelectedVocab(uiState.vocabQuestions[0])
                        viewModel.setupGame()
                        viewModel.changeScreenState(CourseScreenState.Vocabulary)
                    } else if (isGrammarListHasLearningStatus) {
                        viewModel.setSelectedGrammar(uiState.grammarQuestions[0])
                        viewModel.changeScreenState(CourseScreenState.Grammar)
                    } else {
                        viewModel.setSelectedVocab(uiState.vocabQuestions[0])
                        viewModel.setupGame()
                        viewModel.changeScreenState(CourseScreenState.Vocabulary)
                    }
                },
                onBackClicked = onBackClicked
            )
        }
        CourseScreenState.Vocabulary -> {
            CourseVocabulary(
                title = uiState.selectedCourse.name,
                item = uiState.selectedVocab,
                answerList = uiState.answerList,
                onCorrect = { item, isCorrect ->
                    if (isCorrect) {
                        viewModel.updateStatusForVocabItem(item, QuestionStatus.Known)
                    } else {
                        viewModel.updateStatusForVocabItem(item, QuestionStatus.Review)
                    }
                    viewModel.nextQuestion()
                },
                onBackClicked = {
                    viewModel.changeScreenState(CourseScreenState.List)
                }
            )
        }
        CourseScreenState.Grammar -> {
            CourseGrammar(
                title = uiState.selectedCourse.name,
                item = uiState.selectedGrammar,
                onCorrect = { item, isCorrect ->
                    if (isCorrect) {
                        viewModel.updateStatusForGrammarItem(item, QuestionStatus.Known)
                    } else {
                        viewModel.updateStatusForGrammarItem(item, QuestionStatus.Review)
                    }
                    viewModel.nextQuestion()
                },
                onBackClicked = {
                    viewModel.changeScreenState(CourseScreenState.List)
                }
            )
        }
    }
}