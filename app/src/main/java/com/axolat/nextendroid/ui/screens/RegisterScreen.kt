package com.axolat.nextendroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.axolat.nextendroid.data.model.CountryUtils
import com.axolat.nextendroid.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    appLanguage: AppLanguage,
    registrationOpen: Boolean,
    usernameAvailable: Boolean?,
    isLoading: Boolean,
    errorMessage: String?,
    onUsernameChange: (String) -> Unit,
    onRegisterClick: (username: String, email: String, password: String, country: String) -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("FR") }
    var countryDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(username) {
        if (username.length >= 3) {
            delay(350)
            onUsernameChange(username)
        }
    }

    val passwordsMatch = confirmPassword.isEmpty() || password == confirmPassword
    val canSubmit = registrationOpen && !isLoading &&
        username.length >= 3 && email.contains("@") &&
        password.length >= 8 && password == confirmPassword

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(24.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NextendoSurfaceCard, RoundedCornerShape(28.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Strings.registerTitle(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (!registrationOpen) {
                    Text(
                        text = Strings.registrationClosed(appLanguage),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
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

                if (username.length >= 3 && usernameAvailable != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (usernameAvailable) Strings.usernameAvailableText(appLanguage) else Strings.usernameTakenText(appLanguage),
                        color = if (usernameAvailable) Color(0xFF3ECF6E) else MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(Strings.passwordLabel(appLanguage), color = NextendoTextSecondary) },
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

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(Strings.confirmPasswordLabel(appLanguage), color = NextendoTextSecondary) },
                    singleLine = true,
                    isError = !passwordsMatch,
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

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = Strings.country(appLanguage), color = NextendoTextPrimary, fontSize = 15.sp)

                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { countryDropdownExpanded = true }
                        ) {
                            val flag = CountryUtils.getCountryFlag(countryCode)
                            val name = CountryUtils.getCountryName(countryCode)
                            Text(
                                text = "$flag $name",
                                color = NextendoPink,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        DropdownMenu(
                            expanded = countryDropdownExpanded,
                            onDismissRequest = { countryDropdownExpanded = false },
                            modifier = Modifier.background(NextendoSurfaceElevated)
                        ) {
                            CountryUtils.getAllCountries().forEach { (cCode, cName) ->
                                val cFlag = CountryUtils.getCountryFlag(cCode)
                                DropdownMenuItem(
                                    text = { Text(text = "$cFlag $cName", color = NextendoTextPrimary) },
                                    onClick = {
                                        countryDropdownExpanded = false
                                        countryCode = cCode
                                    }
                                )
                            }
                        }
                    }
                }

                if (!errorMessage.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onRegisterClick(username.trim(), email.trim(), password, countryCode) },
                    enabled = canSubmit,
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
                            text = Strings.createAccountButton(appLanguage),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = Strings.alreadyHaveAccount(appLanguage),
                    color = NextendoTextSecondary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { onBackToLoginClick() }
                )
            }
        }
    }
}
