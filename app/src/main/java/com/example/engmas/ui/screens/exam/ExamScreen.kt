package com.example.engmas.ui.screens.exam

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.theme.KufamFont

object ExamDestination: NavigationDestination {
    override val route: String = "exam"
    override val titleRes: Int = R.string.tab_exam
}

@Composable
fun ExamScreen(
    onClicked: (Int) -> Unit,
    viewModel: ExamViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadExams(context)
    }

    val screenState = uiState.screenState

    val iconHeadWeight= 0.25f
    val bodyWeight = 1f
    val itemList = uiState.listName

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC9E9C5)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF4DC5DD), MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .weight(iconHeadWeight)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = dimensionResource(R.dimen.padding_small)),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.toeic_logo),
                            tint = Color.Unspecified,
                            contentDescription = stringResource(R.string.toeic_logo),
                            modifier = Modifier.scale(0.5f)
                        )
                    }
                }
            }
            if (screenState == ExamScreenState.Main) {
                ItemGrid(
                    items = itemList,
                    onClicked = {
                        onClicked(itemList.indexOf(it))
                        Log.d("Exam Screen", "clicked on Item: ${itemList.indexOf(it)}")
                    },
                    modifier = Modifier.weight(bodyWeight)
                )
            } else if (screenState == ExamScreenState.Loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(bodyWeight)
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ItemGrid(
    items: List<String>,
    onClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Or GridCells.Adaptive(minSize = 150.dp)
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_small)),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { name ->
            ItemCard(
                title = name,
                onClicked = { onClicked(name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun ItemCard(
    title: String,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
            Text(
                text = title,
                fontFamily = KufamFont,
                fontWeight = FontWeight.Black,
                fontSize = 14.sp,
                color = Color(0xFF757575),
//                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_smaller))
            )
//            Text(
//                text = stringResource(R.string.completed_count, 1000),
//                fontFamily = KufamFont,
//                fontWeight = FontWeight.Medium,
//                fontSize = 10.sp,
//                color = Color(0xFF757575),
////                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_smaller))
//            )
            Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))
            IconButton(
                onClick = onClicked,
                modifier = Modifier.size(dimensionResource(R.dimen.start_icon_size) + 5.dp)
//                    .padding(vertical = dimensionResource(R.dimen.padding_smaller))
            ) {
                Icon(
                    painter = painterResource(R.drawable.start_icon),
                    tint = Color.Unspecified,
                    contentDescription = stringResource(R.string.start_icon),
                    modifier = Modifier.size(dimensionResource(R.dimen.start_icon_size))
                )
            }
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExamScreenPreview() {
//    ExamScreen()
}