package com.example.engmas.ui.screens.practice.vocabulary

import com.example.engmas.data.model.Topic
import com.example.engmas.data.model.UserScore
import com.example.engmas.ui.screens.practice.courses.data.QuestionVocabItem
import com.example.engmas.ui.screens.practice.vocabulary.model.TopicProgress

data class VocabularyUiState(
    val listTopics: List<TopicProgress> = emptyList(),
    val userScore: UserScore = UserScore(),
    val selectedTopic: Topic = Topic(),
    val listVocabsInTopic: List<QuestionVocabItem> = emptyList(),
    val selectedVocab: QuestionVocabItem = QuestionVocabItem(),
    val answerList: List<String> = emptyList(),
    val isDone: Boolean = false,
    val screenState: VocabularyScreenState = VocabularyScreenState.InTopic
)

enum class VocabularyScreenState {
    ChooseTopic,
    InTopic,
    Flashcard,
    Game
}
