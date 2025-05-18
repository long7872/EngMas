package com.example.engmas.ui.screens.practice.voices.data

import com.example.engmas.ui.screens.practice.voices.data.fluency.Fluency
import com.example.engmas.ui.screens.practice.voices.data.overall.Overall
import com.example.engmas.ui.screens.practice.voices.data.pronunciation.Pronunciation
import com.example.engmas.ui.screens.practice.voices.data.warnings.Metadata
import com.example.engmas.ui.screens.practice.voices.data.warnings.Reading
import com.example.engmas.ui.screens.practice.voices.data.warnings.Warnings
import kotlinx.serialization.Serializable

@Serializable
data class VoiceAnalysisResponse(
    val pronunciation: Pronunciation = Pronunciation(),
    val fluency: Fluency = Fluency(),
    val overall: Overall = Overall(),
    val warnings: Warnings = Warnings(),
    val reading: Reading = Reading(),
    val metadata: Metadata = Metadata()
)
