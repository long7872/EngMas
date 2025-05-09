package com.example.engmas.ui.screens.practice.grammar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.practice.grammar.data.GrammarItem
import com.example.engmas.ui.screens.practice.grammar.data.GrammarItems
import com.example.engmas.ui.utils.TitleRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object PracticeGrammarDestination : NavigationDestination {
    override val route = "practice/grammar"
    override val titleRes = R.string.tab_grammar
    const val ITEM_ARGS = "item"
    val routeWithArgs = "$route/{$ITEM_ARGS}"  // Tạo route với tham số
}

@Composable
fun GrammarContent(
    item: GrammarItem,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleRow(
                containerColor = Color(0xFFE3F2FD),
                itemColor = Color(0xFF757575),
                text = item.title,
                onClick = navigateUp
            )

            Spacer(modifier = Modifier.padding(4.dp))

            ContainerCard(
                item = item,
                onClick = {},
                modifier = Modifier
                    .fillMaxSize()
            )

            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
private fun ContainerCard(
    item: GrammarItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(1.dp, color = Color(0xFFD3D3D3)),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        PdfViewer(item.grammarLink)
//        PDFViewerFromAssets(
//            assetFileName = item.grammarLink,
//            modifier = Modifier
//                .fillMaxSize()
//        )
    }
}

@Composable
fun PdfViewer(fileName: String) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    var currentPage by remember { mutableStateOf(0) }
    var totalPages by remember { mutableStateOf(1) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var scale by remember { mutableStateOf(1f) }
    var initialScaleSet by remember { mutableStateOf(false) }

    // ✅ Render bitmap khi đổi trang
    LaunchedEffect(fileName, currentPage) {
        withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, fileName)
            if (!file.exists()) {
                context.assets.open(fileName).use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
            }

            val descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = PdfRenderer(descriptor)

            totalPages = renderer.pageCount
            val page = renderer.openPage(currentPage)

            val bmp = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            renderer.close()
            descriptor.close()

            bitmap = bmp
//            initialScaleSet = false // ✅ reset scale flag
        }
    }

    // ✅ Tính scale mặc định sau khi bitmap đã render
    LaunchedEffect(bitmap) {
        if (bitmap != null && !initialScaleSet) {
            val screenWidthPx = with(density) { screenWidthDp.toPx() }
            val defaultScale = screenWidthPx / bitmap!!.width
            scale = defaultScale
            initialScaleSet = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔹 Zoom controls
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Button(onClick = { scale += 0.1f }) { Text("Zoom +") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { if (scale > 0.4f) scale -= 0.1f }) { Text("Zoom -") }
        }

        // 🔹 PDF display with zoom via graphicsLayer
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            bitmap?.let {
                val scaledWidth = with(density) { (it.width * scale).toDp() }
                val scaledHeight = with(density) { (it.height * scale).toDp() }

                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "PDF page",
                    modifier = Modifier
                        .size(scaledWidth, scaledHeight)
                        .graphicsLayer(
                            scaleX = 1f,
                            scaleY = 1f,
                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                        )
                )
            } ?: Text("Đang tải PDF...")
        }

        // 🔹 Page navigation
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    if (currentPage > 0) currentPage--
                },
                enabled = currentPage > 0
            ) {
                Text("Previous")
            }

            Text("Trang ${currentPage + 1} / $totalPages")

            Button(
                onClick = {
                    if (currentPage < totalPages - 1) currentPage++
                },
                enabled = currentPage < totalPages - 1
            ) {
                Text("Next")
            }
        }
    }
}

fun renderAllPdfPages(
    context: Context,
    fileName: String,
    scale: Float = 3f // Tăng độ nét (1.0 = gốc, 2.0 = x2 resolution)
): List<Bitmap> {
    val result = mutableListOf<Bitmap>()
    try {
        val file = File(context.cacheDir, fileName)
        if (!file.exists()) {
            context.assets.open(fileName).use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
        }

        val descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(descriptor)

        for (i in 0 until renderer.pageCount) {
            val page = renderer.openPage(i)
            val width = (page.width * scale).toInt()
            val height = (page.height * scale).toInt()
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            result.add(bitmap)
        }

        renderer.close()
        descriptor.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}



@Preview(showBackground = true)
@Composable
private fun CoursesPreview() {
//    ExploreRow(
//        containerColor = Color(0xFFE3F2FD),
//        itemColor = Color(0xFF4DC5DD),
//        iconRes = R.drawable.item_icon,
//        iconDes = R.string.foodicon,
//        text = R.string.food,
//        onClick = {}
//    )
    GrammarContent(
        item = GrammarItems[0],
        navigateUp = {}
    )
}