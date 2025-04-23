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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.engmas.R
import com.example.engmas.ui.navigation.NavigationDestination
import com.example.engmas.ui.theme.EngMasTheme
import com.example.engmas.ui.theme.KufamFont
import com.example.engmas.ui.utils.InputField
import com.example.engmas.ui.utils.PasswordField
import com.example.engmas.ui.utils.SocialIcon

object AuthSignUpDestination: NavigationDestination {
    override val route = "auth/signup"
    override val titleRes = R.string.tab_auth_signup
}

@Composable
fun SignUpScreen(
    onSignUpSuccessfully: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val signUpViewModel: SignUpViewModel = viewModel()
    val signUpState by signUpViewModel.signUpState
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(false) }

    LaunchedEffect(signUpState) {
        when (signUpState) {
            SignUpState.Success -> {
                onSignUpSuccessfully()
            }
            SignUpState.Error -> {
                Toast.makeText(context, signUpViewModel.errorMessage, Toast.LENGTH_SHORT).show()
            }

            SignUpState.Idle -> {}
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
            .padding(bottom = dimensionResource(R.dimen.padding_larger)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            fontSize = 26.sp,
            fontFamily = KufamFont,
            fontWeight = FontWeight.Black,
            color = Color(0xFF2B4EA2),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_larger))
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Text(
            text = "Fill your information below or register\n" +
                    "with your social account.",
            fontSize = 14.sp,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            fontFamily = KufamFont,
            color = Color(0xFF6E6E6E)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        Text(
            text = "Name",
            fontSize = 16.sp,
            fontFamily = KufamFont,
            color = Color(0xFF757575),
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_smaller))
                .align(Alignment.Start)
        )

        InputField(
            textInput = name,
            placeholder = stringResource(R.string.placeholder_name_field),
            onValueChange = { name = it }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

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

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_medium))
                    .align(Alignment.CenterVertically)
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

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))

            Row {
                Text(
                    text = "Agree with ",
                    fontFamily = KufamFont,
                    color = Color(0xFF757575),
                    fontSize = 14.sp
                )
                Text(
                    text = "Terms & Conditions",
                    fontFamily = KufamFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Color(0xFF757575),
                    modifier = Modifier.clickable {

                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        Button(
            onClick = {
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && agree) {
                    signUpViewModel.signUp(name, email, password)
                } else {
                    Toast.makeText(context, "Please fill in all information!", Toast.LENGTH_SHORT).show()
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
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_larger)))

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "  Or sign up with  ",
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
                text = "Already have an account? ",
                fontFamily = KufamFont,
                color = Color(0xFF757575),
                fontSize = 14.sp,
            )
            Text(
                text = "Login",
                fontFamily = KufamFont,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFF757575),
                modifier = Modifier.clickable {
                    onLogin()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onSignUpSuccessfully = {},
        onLogin = {},
        modifier = Modifier.fillMaxSize()
    )
}