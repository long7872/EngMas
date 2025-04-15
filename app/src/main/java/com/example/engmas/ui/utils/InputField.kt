package com.example.engmas.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont

@Composable
fun InputField(
    textInput: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = Color(0xFF757575).copy(alpha = 0.7f)
    val containerColor = Color(0xFFD9D9D9).copy(alpha = 0.63f)
    OutlinedTextField(
        value = textInput,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = KufamFont,
                color = textColor  // Màu placeholder
            )
        },
        textStyle = TextStyle(
            fontFamily = KufamFont,
            color = textColor,
            textAlign = TextAlign.Start,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth()
            .height(dimensionResource(R.dimen.text_field_height))
    )
}

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    InputField(
        textInput = "",
        placeholder = stringResource(R.string.placeholder_email_field),
        onValueChange = {}
    )
}