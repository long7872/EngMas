package com.example.engmas.ui.screens.practice.vocabulary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.data.model.UserLearningStatus
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.courses.data.QuestionStatus

object PracticeVocabularyLearningDestination: NavigationDestination {
    override val route = "practice/vocabulary/learning"
    override val titleRes = R.string.tab_vocabulary_learning
    const val ITEM_ARGS = "topicId"
    val routeWithArgs = "$route/{$ITEM_ARGS}"
}

@Composable
fun VocabularyProcessing(
    topicId: Int,
    onBackClicked: () -> Unit,
    viewModel: VocabularyViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(topicId) {
        viewModel.getVocabsInTopic(topicId)
    }
    val selectedTopic = uiState.selectedTopic
    val listVocabs = uiState.listVocabsInTopic
    val screenState = uiState.screenState

    when (screenState) {
        VocabularyScreenState.ChooseTopic -> {
        }
        VocabularyScreenState.InTopic -> {
            VocabularyInTopic(
                selectedTopic = selectedTopic,
                listVocab = listVocabs,
                onVocabClicked = {
                    viewModel.setSelectedVocab(it)
                    viewModel.changeScreenState(VocabularyScreenState.Flashcard)
                },
                onMarkAllClicked = {
                    viewModel.updateStatusForAllItems(QuestionStatus.Known)
                },
                onStartLearningClicked = {
                    viewModel.setSelectedVocab(uiState.listVocabsInTopic[0])
                    viewModel.setupGame()
                    viewModel.changeScreenState(VocabularyScreenState.Flashcard)
                },
                onBackClicked = onBackClicked
            )
        }
        VocabularyScreenState.Flashcard -> {
            VocabularyFlashcardScreen(
                selectedTopic = selectedTopic,
                item = uiState.selectedVocab,
                onMarkButtonClicked = {
                    viewModel.updateStatusForVocabItem(it, QuestionStatus.Known)
                    viewModel.nextQuestion(hasGame = true, hasDelay = false)
                },
                onPracticeButtonClicked = {
                    viewModel.setupGame()
                    viewModel.changeScreenState(VocabularyScreenState.Game)
                },
                onBackClicked = {
                    viewModel.changeScreenState(VocabularyScreenState.InTopic)
                }
            )
        }
        VocabularyScreenState.Game -> {
            VocabularyGame(
                title = "${selectedTopic.topicName} - ${selectedTopic.topicNameVi}",
                item = uiState.selectedVocab,
                answerList = uiState.answerList,
                onCorrect = { isCorrect ->
                    if (isCorrect) {
                        viewModel.updateStatusForVocabItem(item = uiState.selectedVocab, newStatus = QuestionStatus.Known)
                    } else {
                        viewModel.updateStatusForVocabItem(item = uiState.selectedVocab, newStatus = QuestionStatus.Review)
                    }
                    viewModel.nextQuestion(hasGame = true)
                },
                onBackClicked = {
                    viewModel.changeScreenState(VocabularyScreenState.InTopic)
                }
            )
        }
    }

}