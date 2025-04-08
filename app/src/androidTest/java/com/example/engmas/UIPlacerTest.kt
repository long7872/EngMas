package com.example.engmas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engmas.ui.theme.EngMasTheme

/**
 * Created by codia-figma
 */
@Composable
fun CodiaMainView() {
    // Box-24:55-Challenge
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier.size(720.dp, 1600.dp),
    ) {
        // Empty-24:56-Login
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color(0xfff5f5f5))
                .size(720.dp, 1600.dp),
        )
        // Image-24:57-home-interface-icon_svgrepo.com
        Image(
            painter = painterResource(id = R.drawable.image1_2457),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 59.dp, y = 1491.dp)
                .size(60.dp, 60.dp)
                .clipToBounds(),
        )
        // Image-24:62-book-bookmark_svgrepo.com
        Image(
            painter = painterResource(id = R.drawable.image2_2462),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 194.dp, y = 1491.dp)
                .size(60.dp, 60.dp)
                .clipToBounds(),
        )
        // Image-24:68-i-exam-multiple-choice_svgrepo.com
        Image(
            painter = painterResource(id = R.drawable.image3_2468),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 329.dp, y = 1491.dp)
                .size(60.dp, 60.dp)
                .clipToBounds(),
        )
        // Image-24:78-Group
        Image(
            painter = painterResource(id = R.drawable.image4_2478),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 464.dp, y = 1499.dp)
                .size(61.dp, 43.024.dp),
        )
        // Image-24:87-user_svgrepo.com
        Image(
            painter = painterResource(id = R.drawable.image5_2487),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 600.dp, y = 1491.dp)
                .size(60.dp, 60.dp)
                .clipToBounds(),
        )
        // Text-24:91-Home
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentSize()
                .offset(x = 72.dp, y = 1559.dp),
            text = "Home",
            color = Color(0xff757575),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Left,
            overflow = TextOverflow.Ellipsis,
        )
        // Text-24:92-Practice
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentSize()
                .offset(x = 200.dp, y = 1559.dp),
            text = "Practice",
            color = Color(0xff757575),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Left,
            overflow = TextOverflow.Ellipsis,
        )
        // Text-24:93-Exam
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentSize()
                .offset(x = 341.dp, y = 1559.dp),
            text = "Exam",
            color = Color(0xff757575),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Left,
            overflow = TextOverflow.Ellipsis,
        )
        // Text-24:94-Challenge
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentSize()
                .offset(x = 466.dp, y = 1559.dp),
            text = "Challenge",
            color = Color(0xff2b4ea2),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Left,
            overflow = TextOverflow.Ellipsis,
        )
        // Text-24:95-Account
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentSize()
                .offset(x = 606.dp, y = 1559.dp),
            text = "Account",
            color = Color(0xff757575),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Left,
            overflow = TextOverflow.Ellipsis,
        )
        // Empty-24:99-Rectangle 4
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 37.dp, y = 152.dp)
                .advancedShadow(color = Color(0x3f000000), alpha = 0.25f, cornersRadius = 15.dp, shadowBlurRadius = 15.dp, offsetX = 0.dp, offsetY = 4.dp)
                .background(Color(0xffe3f2fd), RoundedCornerShape(15.dp))
                .size(645.dp, 1252.dp),
        )
        // Empty-24:114-Rectangle 6
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 37.dp, y = 570.dp)
                .advancedShadow(color = Color(0x3f000000), alpha = 0.25f, cornersRadius = 30.dp, shadowBlurRadius = 15.dp, offsetX = 0.dp, offsetY = 4.dp)
                .background(Color(0xff49c1d8), RoundedCornerShape(30.dp))
                .size(645.dp, 834.dp),
        )
        // Empty-24:112-Rectangle 5
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 37.dp, y = 585.dp)
                .advancedShadow(color = Color(0x3f000000), alpha = 0.25f, cornersRadius = 0.dp, shadowBlurRadius = 15.dp, offsetX = 0.dp, offsetY = 4.dp)
                .background(Color(0xffffffff))
                .size(645.dp, 829.dp),
        )
        // Empty-24:96-Rectangle 1
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 99.dp, y = 1165.dp)
                .advancedShadow(color = Color(0x3f000000), alpha = 0.25f, cornersRadius = 15.dp, shadowBlurRadius = 10.dp, offsetX = 0.dp, offsetY = 10.dp)
                .background(Color(0xffffb600), RoundedCornerShape(15.dp))
                .size(510.dp, 150.dp),
        )
        // Empty-24:97-Rectangle 2
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 99.dp, y = 937.dp)
                .advancedShadow(color = Color(0x3f000000), alpha = 0.25f, cornersRadius = 15.dp, shadowBlurRadius = 10.dp, offsetX = 0.dp, offsetY = 10.dp)
                .background(Color(0xffe82e2e), RoundedCornerShape(15.dp))
                .size(510.dp, 150.dp),
        )
        // Empty-24:98-Rectangle 3
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 99.dp, y = 711.dp)
                .advancedShadow(color = Color(0x3f000000), alpha = 0.25f, cornersRadius = 15.dp, shadowBlurRadius = 10.dp, offsetX = 0.dp, offsetY = 10.dp)
                .background(Color(0xff4beb06), RoundedCornerShape(15.dp))
                .size(510.dp, 150.dp),
        )
        // Text-24:100-EngMas
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentSize()
                .offset(x = 37.dp, y = 35.dp),
            text = "EngMas",
            color = Color(0xffffffff),
            fontSize = 64.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
        // Text-24:106-Play Online
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentSize()
                .offset(x = 231.dp, y = 760.dp),
            text = "Play Online",
            color = Color(0xffffffff),
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
        // Text-24:108-Play Offline
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentHeight()
                .offset(x = 209.dp, y = 990.dp)
                .width(290.dp),
            text = "Play Offline",
            color = Color(0xffffffff),
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
        // Text-24:110-Score Board
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .wrapContentHeight()
                .offset(x = 196.dp, y = 1214.dp)
                .width(320.dp),
            text = "Score Board",
            color = Color(0xffffffff),
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
        // Image-12:104-chat-round-call_svgrepo.com
        Image(
            painter = painterResource(id = R.drawable.image6_12104),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 609.dp, y = 35.dp)
                .size(71.dp, 71.dp)
                .clipToBounds(),
        )
        // Image-25:121-Word_Scramble-877x585-removebg-preview 1
        Image(
            painter = painterResource(id = R.drawable.image7_25121),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 62.dp, y = 148.dp)
                .size(612.dp, 408.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CodiaMainViewPreview() {
    EngMasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val scrollState = rememberScrollState()
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                CodiaMainView()
            }
        }
    }
}


fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 0f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        paint.color = Color.Transparent
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}