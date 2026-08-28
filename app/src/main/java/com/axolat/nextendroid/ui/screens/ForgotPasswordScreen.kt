package com.axolat.nextendroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axolat.nextendroid.ui.theme.*

@Composable
fun ForgotPasswordScreen(
    appLanguage: AppLanguage,
    isLoading: Boolean,
    onSendResetLinkClick: (email: String, onResult: (Boolean, String) -> Unit) -> Unit,
    onBackToLoginClick: () -> Unit,
    onHaveCodeClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf<String?>(null) }
    var feedbackIsError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NextendoSurfaceCard, RoundedCornerShape(28.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Strings.forgotPasswordTitle(appLanguage),
                color = NextendoTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = Strings.forgotPasswordDesc(appLanguage),
                color = NextendoTextSecondary,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(Strings.emailLabel(appLanguage), color = NextendoTextSecondary) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NextendoPink,
                    unfocusedBorderColor = NextendoSurfaceVariant,
                    focusedTextColor = NextendoTextPrimary,
                    unfocusedTextColor = NextendoTextPrimary,
                    focusedContainerColor = NextendoSurfaceElevated,
                    unfocusedContainerColor = NextendoSurfaceElevated
                ),
                shape = RoundedCornerShape(16.dp)
            )

            feedback?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = it,
                    color = if (feedbackIsError) MaterialTheme.colorScheme.error else Color(0xFF3ECF6E),
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onSendResetLinkClick(email.trim()) { success, message ->
                        feedback = message
                        feedbackIsError = !success
                    }
                },
                enabled = !isLoading && email.contains("@"),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NextendoPink),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = Strings.sendResetLink(appLanguage),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = Strings.haveCodeAlready(appLanguage),
                color = NextendoPink,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier.clickable { onHaveCodeClick() }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = Strings.backToLogin(appLanguage),
                color = NextendoTextSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier.clickable { onBackToLoginClick() }
            )
        }
    }
}
