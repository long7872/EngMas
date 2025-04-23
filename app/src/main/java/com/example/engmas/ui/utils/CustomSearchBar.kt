package com.example.engmas.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.engmas.R
import com.example.engmas.data.model.Vocab

//@Composable
//fun CSearchBar(
//    query: String,
//    placeholder: String,
//    onQueryChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        elevation = CardDefaults.cardElevation(4.dp),
//        shape = MaterialTheme.shapes.extraLarge,
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
//        modifier = modifier
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFE3F2FD))
//                .padding(horizontal = 16.dp)
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.find_icon),
//                contentDescription = stringResource(R.string.search),
//                tint = Color(0xFF2B4EA2),
//                modifier = Modifier.size(dimensionResource(R.dimen.search_icon_size))
//            )
//            TextField(
//                value = query,
//                onValueChange = onQueryChange,
//                placeholder = {
//                    Text(
//                        text = placeholder,
//                        fontFamily = KufamFont,
//                        fontSize = 14.sp,
//                        color = Color(0xFF757575)
//                    )
//                },
//                textStyle = TextStyle(
//                    fontFamily = KufamFont,
//                    fontSize = 14.sp,
//                    color = Color(0xFF757575),
//                    textAlign = TextAlign.Start,
//                ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color(0xFFE0F0FF),
//                    unfocusedContainerColor = Color(0xFFE0F0FF),
//                    focusedIndicatorColor = Color(0xFFE0F0FF),
//                    unfocusedIndicatorColor = Color(0xFFE0F0FF),
//                    disabledIndicatorColor = Color(0xFFE0F0FF),
//                ),
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}


//@Composable
//fun SearchBar(
//    query: String,
//    placeholder: String,
//    onQueryChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    OutlinedTextField(
//        value = query,
//        onValueChange = onQueryChange,
//        placeholder = {
//            Text(
//                text = placeholder,
//                color = Color(0xFF757575),
//                fontSize = 14.sp
//            )
//        },
//        leadingIcon = {
//            Icon(
//                painter = painterResource(R.drawable.find_icon),
//                contentDescription = "Search Icon",
//                tint = Color(0xFF1E88E5),
//                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_smaller))
//                    .size(dimensionResource(R.dimen.search_icon_size))
//            )
//        },
//        singleLine = true,
//        shape = RoundedCornerShape(50), // Bo tròn góc
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = Color(0xFFB3E5FC),
//            unfocusedBorderColor = Color(0xFFB3E5FC),
//            focusedContainerColor = Color(0xFFE3F2FD),
//            unfocusedContainerColor = Color(0xFFE3F2FD),
//            cursorColor = Color(0xFF1E88E5),
//            focusedLeadingIconColor = Color(0xFF1E88E5),
//            unfocusedLeadingIconColor = Color(0xFF1E88E5),
//            focusedTextColor = Color.Black,
//            unfocusedTextColor = Color.Black,
//            focusedPlaceholderColor = Color(0xFF757575),
//            unfocusedPlaceholderColor = Color(0xFF757575)
//        ),
//        modifier = modifier
//            .fillMaxWidth()
//            .height(60.dp)
//    )
//}

data class Test(
    val first: String,
    val second: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onClearButton: () -> Unit,
    queryItems: List<Vocab>,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = isActive,
        onActiveChange = { onActiveChange(it) },
        placeholder = { Text("Search...") },
        leadingIcon = {
//            Icon(
//                Icons.Default.Search,
//                contentDescription = stringResource(R.string.search)
//            )
            Icon(
                painter = painterResource(R.drawable.find_icon),
                contentDescription = stringResource(R.string.search),
                tint = Color(0xFF1E88E5),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_smaller))
                    .size(dimensionResource(R.dimen.search_icon_size))
            )
        },
        trailingIcon = {
            if (query.isEmpty()) {
                Icon(Icons.Default.Mic, "stringResource(R.string.micro)")
            } else {
                IconButton(onClick = {
                    onClearButton()
                    onActiveChange(false)
                }) {
                    Icon(Icons.Default.Clear, "stringResource(R.string.clear)")
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFDDE7F3), // Luôn giữ màu nền ô nhập
            dividerColor = Color.Transparent
        ),
        windowInsets = WindowInsets(0.dp),
        tonalElevation = 0.dp, // Xóa màu nền của phần gợi ý
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .clip(MaterialTheme.shapes.large)
    ) {
        LazyColumn(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            items(queryItems) { item ->
                QueryRow(
                    iataCode = item.word,
                    name = item.phonetic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Gán query = item nếu cần, rồi tắt active
                            onQueryChange(item.word)
                            onItemClicked()
                            onActiveChange(false)
                        }
                        .padding(dimensionResource(R.dimen.padding_smaller))
                )
            }
        }
    }
}

@Composable
private fun QueryRow(iataCode: String, name: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = iataCode,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = name,
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
//    SearchBar(
//        query = "",
//        placeholder = stringResource(R.string.search_placeholder),
//        onQueryChange = {}
//    )
//    val testList = listOf(
//        Test("hehe", "vvvv"),
//        Test("haha", "xxxx"),
//        Test("huhu", "eeee")
//    )
//    var query by rememberSaveable { mutableStateOf("") }
//    var isActive by rememberSaveable { mutableStateOf(false) }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//        CustomSearchBar(
//            query = query,
//            onQueryChange = { query = it },
//            isActive = isActive,
//            onActiveChange = { isActive = it },
//            onClearButton = { query = "" },
//            queryItems = testList,
//            modifier = Modifier.fillMaxWidth()
//                .align(Alignment.TopCenter)
//        )
//    }

}
