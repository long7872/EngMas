package com.example.engmas.ui.screens.home.chatbot_deepseek

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatInterface(viewModel: ChatbotViewModel = viewModel()) {
    val messages by viewModel.messages.observeAsState(emptyList())
    val uiState by viewModel.uiState.observeAsState(UIState.Idle)
    val inputText = remember { mutableStateOf("") }
    val isSending = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Message - Chat", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            messages.forEach { (text, isUser) ->
                MessageBubble(text, isUser)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is UIState.Loading -> {
                Text("Sending message...", style = MaterialTheme.typography.bodyLarge)
            }
            is UIState.Error -> {
                Text("Error: ${(uiState as UIState.Error).message}", style = MaterialTheme.typography.bodyLarge, color = Color.Red)
            }
            else -> Unit
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BasicTextField(
                value = inputText.value,
                onValueChange = { inputText.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Gray, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
            )

            Button(
                onClick = {
                    if (inputText.value.isNotBlank()) {
                        isSending.value = true
                        viewModel.sendMessage(inputText.value)
                        inputText.value = "" // Clear the input field
                    }
                },
                enabled = !isSending.value
            ) {
                Text(text = if (isSending.value) "Sending..." else "Send")
            }
        }
    }
}

@Composable
fun MessageBubble(message: String, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = message,
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = if (isUser) Color(0xFFDCF8C6) else Color(0xFFF1F0F0),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            style = TextStyle(fontSize = 16.sp)
        )
    }
}
