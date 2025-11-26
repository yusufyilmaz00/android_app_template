package com.unluckyprayers.associumhub.ui.screen.login

import androidx.compose.animation.AnimatedVisibility
import com.unluckyprayers.associumhub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unluckyprayers.associumhub.data.local.model.UserState
import com.unluckyprayers.associumhub.ui.theme.*

@Composable
fun LoginUI(
    modifier: Modifier = Modifier,
    state: LoginUIState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onGoogleSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    val isLoading = state.authState is UserState.Loading

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        Gradient()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()

            Spacer(modifier = Modifier.height(40.dp))

            GoogleSignInButton(onClick = onGoogleSignInClicked)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 30.dp)
            ) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)))
                Text("Or", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(horizontal = 10.dp))
                Box(modifier = Modifier.weight(1f).height(1.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)))
            }

            // Email ve Password alanları aynı kalabilir
            Column(horizontalAlignment = Alignment.Start) {
                Text("Email", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    enabled = !isLoading,
                    placeholder = { Text("john.doe@example.com", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(horizontalAlignment = Alignment.Start) {
                Text("Password", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    enabled = !isLoading,
                    placeholder = { Text("Enter your password", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            // Buton "Log in" olarak değişti
            Button(
                onClick = onLoginClicked,
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedVisibility(visible = isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                }
                AnimatedVisibility(visible = !isLoading) {
                    Text("Log In", modifier = Modifier.padding(vertical = 4.dp)) // Değişti
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // TextButton "Sign up" olarak değişti
            TextButton(onClick = onSignUpClicked, enabled = !isLoading) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light, color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                            append("Don't have an account? ") // Değişti
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)) {
                            append("Sign up") // Değişti
                        }
                    }
                )
            }
        }
    }
}

// Header başlıkları değişti
@Composable
private fun LoginHeader() {
    Text(
        text = "Welcome Back!", // Değişti
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Enter your credentials to continue", // Değişti
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

// Bu fonksiyonlar aynı kalabilir
@Composable
private fun GoogleSignInButton(onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
        Image(painter = painterResource(R.drawable.ic_google), contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text("Sign In With Google", color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
fun Gradient() {
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.35f).background(brush = Brush.verticalGradient(colors = listOf(AssociumSecondary, AssociumPrimary, AssociumBackground))))
}


@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    AssociumTheme {
        LoginUI(
            state = LoginUIState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClicked = {},
            onGoogleSignInClicked = {},
            onSignUpClicked = {}
        )
    }
}