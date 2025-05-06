package com.example.engmas.ui.screens.practice.vocabulary

import com.example.engmas.data.model.Topic
import com.example.engmas.data.model.Vocab
import com.example.engmas.ui.screens.practice.vocabulary.model.TopicProgress
import com.example.engmas.ui.screens.practice.vocabulary.model.VocabLearningInTopic

data class VocabularyUiState(
    val listTopics: List<TopicProgress> = emptyList(),
    val selectedTopic: Topic = Topic(),
    val listVocabsInTopic: List<VocabLearningInTopic> = emptyList(),
    val selectedVocab: VocabLearningInTopic = VocabLearningInTopic(),
    val answerList: List<String> = emptyList(),
    val screenState: VocabularyScreenState = VocabularyScreenState.InTopic
)

enum class VocabularyScreenState {
    ChooseTopic,
    InTopic,
    Flashcard,
    Game
}
