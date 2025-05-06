package com.example.engmas.ui.screens.practice.vocabulary

import android.util.Log
import android.widget.Toast
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
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabLearningStatus

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

    val groupedVocabs = listVocabs.groupBy { it.status }
    val exploreGroup = groupedVocabs[VocabLearningStatus.Explore] ?: emptyList()
    val doneGroup = groupedVocabs[VocabLearningStatus.Done] ?: emptyList()

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
                    viewModel.insertVocabLearning(vocabId = it.id)
                    Log.d("Vocabulary Processing", "call insert ${uiState.selectedVocab.id} ${uiState.selectedVocab.word}")
                },
                onMarkAllClicked = {
                    exploreGroup.forEach { viewModel.insertVocabLearning(it.id, status = UserLearningStatus.Known) }
                },
                onStartLearningClicked = {
                    if (exploreGroup.isEmpty()) {
                        Toast.makeText(context, "All vocabularies are learned", Toast.LENGTH_SHORT).show()
                    } else {
                        val selected = exploreGroup.first()
                        viewModel.setSelectedVocab(selected)
                        viewModel.changeScreenState(VocabularyScreenState.Flashcard)
                        viewModel.insertVocabLearning(vocabId = selected.id)
                        Log.d("Vocabulary Processing", "call insert on click start button: " +
                                "${uiState.selectedVocab.id} ${uiState.selectedVocab.word} check: " +
                                "${exploreGroup.first().id} ${exploreGroup.first().word}")
                    }
                },
                onBackClicked = onBackClicked
            )
        }
        VocabularyScreenState.Flashcard -> {
            VocabularyFlashcardScreen(
                selectedTopic = selectedTopic,
                item = uiState.selectedVocab,
                onMarkButtonClicked = {
                    viewModel.updateVocabLearning(uiState.selectedVocab.id, status = UserLearningStatus.Known)
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
                selectedTopic = selectedTopic,
                item = uiState.selectedVocab,
                answerList = uiState.answerList,
                onCorrect = { isCorrect ->
                    if (isCorrect) {
                        viewModel.updateVocabLearning(vocabId = uiState.selectedVocab.id, status = UserLearningStatus.Known)
                    } else {
                        viewModel.updateVocabLearning(vocabId = uiState.selectedVocab.id, status = UserLearningStatus.Review)
                    }
                },
                onBackClicked = {
                    viewModel.changeScreenState(VocabularyScreenState.InTopic)
                }
            )
        }
    }

}