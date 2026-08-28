package com.axolat.nextendroid.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axolat.nextendroid.ui.theme.*

@Composable
fun LoginScreen(
    onLoginClick: (email: String, pw: String) -> Unit,
    isLoading: Boolean,
    errorMessage: String? = null,
    appLanguage: AppLanguage = AppLanguage.FR,
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onGuestLoginClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cardVisible by remember { mutableStateOf(false) }
    var guestFieldOpen by remember { mutableStateOf(false) }
    var guestUsername by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        cardVisible = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = cardVisible,
            enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 6 }
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NextendoSurfaceCard, RoundedCornerShape(28.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nextendo Network",
                color = NextendoTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reprends la partie",
                color = NextendoPink,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Connecte-toi à ton compte Nextendo pour retrouver tes amis et ton friend code.",
                color = NextendoTextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail ou Pseudo", color = NextendoTextSecondary) },
                singleLine = true,
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mot de passe", color = NextendoTextSecondary) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = Strings.forgotPasswordLink(appLanguage),
                color = NextendoTextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onForgotPasswordClick() }
            )

            AnimatedVisibility(
                visible = !errorMessage.isNullOrEmpty(),
                enter = fadeIn(tween(200)) + expandVertically(tween(200)),
                exit = fadeOut(tween(150)) + shrinkVertically(tween(150))
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = errorMessage.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onLoginClick(email, password) },
                enabled = !isLoading && email.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NextendoPink
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Connexion",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = Strings.noAccountYet(appLanguage),
                color = NextendoPink,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier.clickable { onRegisterClick() }
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = NextendoDivider)
            Spacer(modifier = Modifier.height(12.dp))

            if (guestFieldOpen) {
                OutlinedTextField(
                    value = guestUsername,
                    onValueChange = { guestUsername = it },
                    label = { Text(Strings.usernameLabel(appLanguage), color = NextendoTextSecondary) },
                    singleLine = true,
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
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { onGuestLoginClick(guestUsername.trim()) },
                    enabled = !isLoading && guestUsername.trim().length >= 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NextendoSurfaceElevated),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = Strings.continueAsGuest(appLanguage),
                        color = NextendoTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            } else {
                TextButton(
                    onClick = { guestFieldOpen = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = Strings.continueAsGuest(appLanguage),
                        color = NextendoTextSecondary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
        }
    }
}
