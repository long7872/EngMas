package com.example.engmas.ui.screens.auth.signup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.engmas.R
import com.example.engmas.ui.screens.account.AccountDestination
import com.example.engmas.ui.screens.auth.login.SocialLoginIcon
import com.example.engmas.ui.theme.EngMasTheme

val kufamFont = FontFamily(
    Font(R.font.kufam_black, FontWeight.Black),
    Font(R.font.kufam_bold, FontWeight.Bold),
    Font(R.font.kufam_semibold, FontWeight.SemiBold),
    Font(R.font.kufam_medium, FontWeight.Medium),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController, // Thêm tham số này
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val signUpViewModel: SignUpViewModel = viewModel()
    val signUpState by signUpViewModel.signUpState
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(false) }


    // enum, ss, ẻr, null
    // when
    LaunchedEffect(signUpState) {
        when {
            signUpState == "Success" -> {
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
            signUpState.startsWith("Error") -> {
                Toast.makeText(context, signUpState, Toast.LENGTH_SHORT).show()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
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
            text = "Fill your information below or register\nwith your social account.",
            fontSize = 14.sp,
            lineHeight = 18.sp,  // Giảm khoảng cách dòng
            textAlign = TextAlign.Center,
            fontFamily = kufamFont,
            color = Color(0xFF6E6E6E)
        )

        Spacer(modifier = Modifier.height(24.dp))



        Text(
            text = "Name",
            fontSize = 16.sp,
            fontFamily = kufamFont,
            color = Color(0xFF757575),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = {
                Text(
                    "John Doe",
                    fontFamily = kufamFont,
                    color = Color(0xFF979797)  // Màu placeholder
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFD9D9D9),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),  // Bo góc
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

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


        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically) // Cho nằm giữa hàng
            ) {
                Checkbox(
                    checked = agree,
                    onCheckedChange = { agree = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF2B4EA2),
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                buildAnnotatedString {
                    append("Agree with ")

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            color = Color(0xFF757575)
                        )
                    ) {
                        append("Terms & Conditions")
                    }
                },
                fontFamily = kufamFont,
                color = Color(0xFF757575),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && agree) {
                    signUpViewModel.signUp(name, email, password)
                } else {
                    Toast.makeText(context, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2B4EA2))
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold // Làm chữ in đậm
            )
        }

        Spacer(modifier = Modifier.height(72.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = "  Or sign up with  ",
                color = Color(0xFF757575) // Đặt màu chữ là #757575
            )
            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialLoginIcon(R.drawable.facebook)
            SocialLoginIcon(R.drawable.google)
        }

        Spacer(modifier = Modifier.height(42.dp))

        Text(
            buildAnnotatedString {
                append("Already have an account? ")

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF757575)
                    )
                ) {
                    append("Login")
                }
            },
            fontFamily = kufamFont,
            color = Color(0xFF757575),
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                navController.navigate("login")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    EngMasTheme {
        SignUpScreen(
            navController = rememberNavController(), // Dùng tạm trong preview (không thực sự điều hướng)
            modifier = Modifier.fillMaxSize()
        )
    }
}