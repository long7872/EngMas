package com.example.engmas.ui.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.engmas.R.*
import com.example.engmas.ui.screens.auth.signup.kufamFont
import com.example.engmas.ui.theme.EngMasTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.engmas.ui.screens.account.AccountDestination
import com.example.engmas.ui.screens.home.HomeDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var remember by remember { mutableStateOf(false) }
    val loginState by loginViewModel.loginState

    LaunchedEffect(loginState) {
        if (loginState == "Success") {
            navController.navigate(HomeDestination.route)
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Thêm khả năng cuộn dọc
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            fontSize = 26.sp, // Tăng fontSize để nét dày hơn (thử 30.sp, 36.sp, v.v.)
            fontFamily = kufamFont,
            fontWeight = FontWeight.Black, // Dùng weight cao nhất (900) nếu font hỗ trợ
            color = Color(0xFF2B4EA2),
            style = TextStyle(
                fontWeight = FontWeight.Black // Đảm bảo trong TextStyle nếu cần
            ),
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Hi! Welcome back, you've been missed",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = kufamFont,
            color = Color(0xFF6E6E6E)
        )

        Spacer(modifier = Modifier.height(32.dp))


        // Email
        Text(
            text = "Email",
            fontSize = 16.sp,
            fontFamily = kufamFont,
            color = Color(0xFF757575),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(
                    "example@gmail.com",
                    fontFamily = kufamFont,
                    color = Color(0xFF979797)  // Màu placeholder
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFD9D9D9),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Password
        Text(
            text = "Password",
            fontSize = 16.sp,
            fontFamily = kufamFont,
            color = Color(0xFF757575),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text(
                    "*******************",
                    fontFamily = kufamFont,
                    color = Color(0xFF979797)  // Màu placeholder
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFD9D9D9),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forgot Password?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold, // In đậm
            textDecoration = TextDecoration.Underline, // Gạch chân
            fontFamily = kufamFont,
            color = Color(0xFF6E6E6E), // Giữ nguyên màu
            textAlign = TextAlign.End, // Căn phải
            modifier = Modifier
                .fillMaxWidth() // Đảm bảo Text chiếm toàn chiều ngang để căn phải hiệu quả
                .clickable {
                    // Xử lý sự kiện khi nhấn vào đây, ví dụ:
                    println("Forgot Password clicked!")
                }
        )

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = {
                loginViewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2B4EA2)),
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold // Làm chữ in đậm
            )
        }

        Spacer(modifier = Modifier.height(152.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = "  Or login with  ",
                color = Color(0xFF757575) // Đặt màu chữ là #757575
            )
            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialLoginIcon(drawable.facebook)
            SocialLoginIcon(drawable.google)
        }

        Spacer(modifier = Modifier.height(58.dp))

        Text(
            buildAnnotatedString {
                append("Don't have an account? ")

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF757575)
                    )
                ) {
                    append("Sign up")
                }
            },
            fontFamily = kufamFont,
            color = Color(0xFF757575),
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                // Code chuyển hướng sang màn hình Sign Up
                // Ví dụ nếu dùng Navigation Compose:
                navController.navigate("signup")
            }
        )
    }
}

@Composable
fun SocialLoginIcon(icon: Int) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(42.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EngMasTheme {
        LoginScreen(
            navController = rememberNavController(), // Dùng tạm trong preview (không thực sự điều hướng)
            modifier = Modifier.fillMaxSize()
        )
    }
}