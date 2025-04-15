package com.example.engmas.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont

@Composable
fun PasswordField(
    password: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showPassword by remember { mutableStateOf(false) }
    val textColor = Color(0xFF757575).copy(alpha = 0.7f)
    val containerColor = Color(0xFFD9D9D9).copy(alpha = 0.63f)

    OutlinedTextField(
        value = password,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                stringResource(R.string.placeholder_password_field),
                fontFamily = KufamFont,
                color = textColor  // Màu placeholder
            )
        },
        textStyle = TextStyle(
            fontFamily = KufamFont,
            color = textColor,
            textAlign = TextAlign.Start,
        ),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(
                onClick = { showPassword = !showPassword },
                modifier = Modifier.width(dimensionResource(R.dimen.hide_seek_icon_size))
            ) {
                Icon(
                    painter = painterResource(
                        if (showPassword) R.drawable.hide
                        else R.drawable.seek
                    ),
                    contentDescription = stringResource(
                        if (showPassword) R.string.hide_icon
                        else R.string.seek_icon
                    ),
                    modifier = Modifier.width(dimensionResource(R.dimen.hide_seek_icon_size))
                )
            }
        },
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
private fun PasswordFieldPreview() {
    PasswordField(
        password = "",
        onValueChanged = {}
    )
}