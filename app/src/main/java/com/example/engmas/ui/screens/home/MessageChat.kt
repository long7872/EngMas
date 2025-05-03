package com.example.engmas.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.EngMasTheme

@Composable
fun ChatInterface(viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val messages by viewModel.messages.collectAsState()
    val inputText = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Message - Chat", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            messages.forEach { (text, isUser) ->
                MessageBubble(text, isUser)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BasicTextField(
                value = inputText.value,
                onValueChange = { inputText.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
            )

            Button(onClick = {
                if (inputText.value.isNotBlank()) {
                    viewModel.sendMessage(inputText.value)
                    inputText.value = ""
                }
            }) {
                Text(text = "Send")
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
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            style = TextStyle(fontSize = 16.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowChatInterface() {
    EngMasTheme {
        ChatInterface()
    }
}
