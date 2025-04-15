package com.example.engmas.ui.screens.auth.login

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.screens.home.HomeDestination
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.InputField
import com.example.engmas.ui.utils.PasswordField
import com.example.engmas.ui.utils.SocialIcon

object AuthLoginDestination: NavigationDestination {
    override val route = "auth/login"
    override val titleRes = R.string.tab_auth_login
}

@Composable
fun LoginScreen(
    onSignUp: () -> Unit,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
            .padding(bottom = dimensionResource(R.dimen.padding_larger)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            fontSize = 26.sp,
            fontFamily = KufamFont,
            fontWeight = FontWeight.Black,
            color = Color(0xFF2B4EA2),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_larger))
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Text(
            text = "Hi! Welcome back, you've been missed",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = KufamFont,
            color = Color(0xFF000000).copy(alpha = 0.55f)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))


        // Email
        Text(
            text = "Email",
            fontSize = 16.sp,
            fontFamily = KufamFont,
            color = Color(0xFF757575),
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_smaller))
                .align(Alignment.Start)
        )

        InputField(
            textInput = email,
            placeholder = stringResource(R.string.placeholder_email_field),
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        // Password
        Text(
            text = "Password",
            fontSize = 16.sp,
            fontFamily = KufamFont,
            color = Color(0xFF757575),
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_smaller))
                .align(Alignment.Start)
        )

        PasswordField(
            password = password,
            onValueChanged = { password = it }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "Forgot Password?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontFamily = KufamFont,
                color = Color(0xFF757575),
                modifier = Modifier.clickable {
                }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))


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
                text = stringResource(R.string.tab_auth_login),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "  Or login with  ",
                color = Color(0xFF757575)
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialIcon(R.drawable.facebook)
            Spacer(Modifier.width(dimensionResource(R.dimen.padding_larger) * 2))
            SocialIcon(R.drawable.google)
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

        Row {
            Text(
                text = "Don't have an account? ",
                fontFamily = KufamFont,
                color = Color(0xFF757575),
                fontSize = 14.sp,
            )
            Text(
                text = "Sign up",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFF757575),
                modifier = Modifier.clickable {
                    // Ví dụ nếu dùng Navigation Compose:
                    onSignUp()
                }
            )
        }
    }



}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onSignUp = {},
        loginViewModel = viewModel(),
        modifier = Modifier.fillMaxSize()
    )
}