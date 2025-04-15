package com.example.engmas.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.R
import com.example.engmas.ui.theme.KufamFont

@Composable
fun CSearchBar(
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FD))
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.find_icon),
                contentDescription = stringResource(R.string.search),
                tint = Color(0xFF2B4EA2),
                modifier = Modifier.size(dimensionResource(R.dimen.search_icon_size))
            )
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        text = placeholder,
                        fontFamily = KufamFont,
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
                },
                textStyle = TextStyle(
                    fontFamily = KufamFont,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.Start,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0F0FF),
                    unfocusedContainerColor = Color(0xFFE0F0FF),
                    focusedIndicatorColor = Color(0xFFE0F0FF),
                    unfocusedIndicatorColor = Color(0xFFE0F0FF),
                    disabledIndicatorColor = Color(0xFFE0F0FF),
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFF757575),
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.find_icon),
                contentDescription = "Search Icon",
                tint = Color(0xFF1E88E5) // xanh đậm giống hình
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(50), // Bo tròn góc
        colors = OutlinedTextFieldDefaults.colors( // CHUẨN MỚI
            focusedBorderColor = Color(0xFFB3E5FC),
            unfocusedBorderColor = Color(0xFFB3E5FC),
            focusedContainerColor = Color(0xFFE3F2FD),
            unfocusedContainerColor = Color(0xFFE3F2FD),
            cursorColor = Color(0xFF1E88E5),
            focusedLeadingIconColor = Color(0xFF1E88E5),
            unfocusedLeadingIconColor = Color(0xFF1E88E5),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedPlaceholderColor = Color(0xFF757575),
            unfocusedPlaceholderColor = Color(0xFF757575)
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    )
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar(
        query = "",
        placeholder = stringResource(R.string.search_placeholder),
        onQueryChange = {}
    )
}
