package com.axolat.nextendroid.ui.screens

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.scale
import com.axolat.nextendroid.data.model.CountryUtils
import com.axolat.nextendroid.data.model.GameDictionary
import com.axolat.nextendroid.data.model.NetworkStatus
import com.axolat.nextendroid.data.model.UserAccount
import com.axolat.nextendroid.ui.components.AvatarView
import com.axolat.nextendroid.ui.theme.*
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    userAccount: UserAccount?,
    appLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    networkStatus: NetworkStatus = NetworkStatus.CHECKING,
    modifier: Modifier = Modifier,
    onSaveUsernameClick: (String) -> Unit = {},
    onSaveCountryClick: (String) -> Unit = {},
    onSaveProfileImageClick: (String) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onSessionsClick: () -> Unit = {},
    onResendVerificationClick: ((Boolean, String) -> Unit) -> Unit = { _ -> },
    onChangeEmailClick: (String, String, (Boolean, String) -> Unit) -> Unit = { _, _, _ -> },
    onDeleteAccountClick: (String, (Boolean, String) -> Unit) -> Unit = { _, _ -> },
    onLogoutClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var isEditingUsername by remember { mutableStateOf(false) }
    var usernameInput by remember { mutableStateOf(userAccount?.displayUsername ?: "Axolat") }
    var countryDropdownExpanded by remember { mutableStateOf(false) }
    var languageDropdownExpanded by remember { mutableStateOf(false) }
    var showChangeEmailDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

    LaunchedEffect(userAccount) {
        if (!isEditingUsername) {
            usernameInput = userAccount?.displayUsername ?: "Axolat"
        }
    }

    // Photo Picker Launcher for Profile Picture (PP) Change
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }
                val scaled = bitmap.scale(512, 512)
                val stream = ByteArrayOutputStream()
                scaled.compress(android.graphics.Bitmap.CompressFormat.JPEG, 85, stream)
                val bytes = stream.toByteArray()
                val base64Str = Base64.encodeToString(bytes, Base64.NO_WRAP)
                onSaveProfileImageClick(base64Str)
                Toast.makeText(context, "Photo de profil mise à jour !", Toast.LENGTH_SHORT).show()
            } catch (_: Exception) {
                Toast.makeText(context, "Erreur lors de la sélection d'image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // App Bar Title + Gear Settings Icon
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = Strings.accountTitle(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Paramètres",
                        tint = NextendoTextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Profile Header Card (Avatar with Edit PP overlay button, Username, FriendCode, Booster badge)
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(24.dp)
            ) {
                Box(
                    modifier = Modifier.clickable {
                        photoPickerLauncher.launch("image/*")
                    }
                ) {
                    AvatarView(
                        username = userAccount?.displayUsername ?: "Axolat",
                        avatarUrl = GameDictionary.getAvatarUrl(userAccount?.image, userAccount?.avatar),
                        size = 84.dp,
                        showOnlineDot = false
                    )

                    // Edit Camera Icon Overlay Button
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(NextendoPink),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = Strings.changeProfilePicture(appLanguage),
                            tint = NextendoDarkBackground,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = Strings.changeProfilePicture(appLanguage),
                    color = NextendoPink,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        photoPickerLauncher.launch("image/*")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Editable Username Header
                if (isEditingUsername) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = usernameInput,
                            onValueChange = { usernameInput = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = NextendoTextPrimary,
                                unfocusedTextColor = NextendoTextPrimary,
                                focusedBorderColor = NextendoPink,
                                unfocusedBorderColor = NextendoTextSecondary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        IconButton(
                            onClick = {
                                if (usernameInput.isNotBlank()) {
                                    onSaveUsernameClick(usernameInput.trim())
                                    isEditingUsername = false
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Sauvegarder pseudo",
                                tint = NextendoPink
                            )
                        }
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = userAccount?.displayUsername ?: "Axolat",
                            color = NextendoTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        IconButton(
                            onClick = { isEditingUsername = true },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Modifier pseudo",
                                tint = NextendoTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = userAccount?.friendCode ?: "SW-5094-0594-9846",
                        color = NextendoTextSecondary,
                        fontSize = 14.sp
                    )

                    val flagStr = CountryUtils.getCountryFlag(userAccount?.country ?: "FR")
                    if (flagStr.isNotBlank()) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = flagStr,
                            fontSize = 16.sp
                        )
                    }
                }

                if (userAccount?.isBooster == true) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(NextendoPink.copy(alpha = 0.15f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "🚀 ${Strings.boosterBadge(appLanguage)}",
                            color = NextendoPink,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Section Title: Compte
        item {
            Text(
                text = Strings.accountTitle(appLanguage),
                color = NextendoTextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
            )
        }

        // Account Details Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
            ) {
                // Email
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Email", color = NextendoTextPrimary, fontSize = 15.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = userAccount?.email ?: "axolat@nextendo.network",
                                color = NextendoTextSecondary,
                                fontSize = 14.sp
                            )
                            val emailLinked = userAccount?.emailVerified == true
                            Text(
                                text = if (emailLinked) Strings.linked(appLanguage) else Strings.notLinked(appLanguage),
                                color = if (emailLinked) NextendoPink else NextendoTextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        IconButton(
                            onClick = { showChangeEmailDialog = true },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = Strings.changeEmailTitle(appLanguage),
                                tint = NextendoTextSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                if (userAccount != null && !userAccount.emailVerified) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 0.dp)
                            .clickable {
                                onResendVerificationClick { _, message ->
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            },
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = Strings.resendVerificationButton(appLanguage),
                            color = NextendoPink,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }

                HorizontalDivider(color = NextendoDivider, modifier = Modifier.padding(horizontal = 16.dp))

                // Discord
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = Strings.discord(appLanguage), color = NextendoTextPrimary, fontSize = 15.sp)
                    Column(horizontalAlignment = Alignment.End) {
                        val discordLinked = userAccount?.isDiscordLinked == true
                        Text(
                            text = if (discordLinked) {
                                userAccount.discordUsername ?: userAccount.discordId ?: Strings.linked(appLanguage)
                            } else {
                                Strings.notLinked(appLanguage)
                            },
                            color = NextendoTextSecondary,
                            fontSize = 14.sp
                        )
                        if (discordLinked) {
                            Text(
                                text = Strings.linked(appLanguage),
                                color = NextendoPink,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                HorizontalDivider(color = NextendoDivider, modifier = Modifier.padding(horizontal = 16.dp))

                // PID
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "PID", color = NextendoTextPrimary, fontSize = 15.sp)
                    Text(
                        text = "${userAccount?.pid ?: 509405949846L}",
                        color = NextendoTextSecondary,
                        fontSize = 14.sp
                    )
                }

                HorizontalDivider(color = NextendoDivider, modifier = Modifier.padding(horizontal = 16.dp))

                // Editable Country Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = Strings.country(appLanguage), color = NextendoTextPrimary, fontSize = 15.sp)

                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { countryDropdownExpanded = true }
                        ) {
                            val code = userAccount?.country ?: "FR"
                            val flag = CountryUtils.getCountryFlag(code)
                            val name = CountryUtils.getCountryName(code)
                            Text(
                                text = "$flag $name ($code)",
                                color = NextendoPink,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Choisir le pays",
                                tint = NextendoPink
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
                                    text = {
                                        Text(
                                            text = "$cFlag $cName ($cCode)",
                                            color = NextendoTextPrimary
                                        )
                                    },
                                    onClick = {
                                        countryDropdownExpanded = false
                                        onSaveCountryClick(cCode)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title: Langue / Language
        item {
            Text(
                text = Strings.language(appLanguage),
                color = NextendoTextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )
        }

        // Language Dropdown Selector Card
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = Strings.language(appLanguage), color = NextendoTextPrimary, fontSize = 15.sp)

                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { languageDropdownExpanded = true }
                    ) {
                        Text(
                            text = "${appLanguage.flag} ${appLanguage.displayName}",
                            color = NextendoPink,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = Strings.language(appLanguage),
                            tint = NextendoPink
                        )
                    }

                    DropdownMenu(
                        expanded = languageDropdownExpanded,
                        onDismissRequest = { languageDropdownExpanded = false },
                        modifier = Modifier.background(NextendoSurfaceElevated)
                    ) {
                        AppLanguage.entries.forEach { lang ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${lang.flag} ${lang.displayName}",
                                        color = NextendoTextPrimary,
                                        fontWeight = if (appLanguage == lang) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    languageDropdownExpanded = false
                                    onLanguageChange(lang)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Section Title: Network Status
        item {
            Text(
                text = Strings.networkStatusTitle(appLanguage),
                color = NextendoTextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )
        }

        // Network Status Card (Nextendo server reachability)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Nextendo", color = NextendoTextPrimary, fontSize = 15.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val targetDotColor = when (networkStatus) {
                        NetworkStatus.OPERATIONAL -> Color(0xFF3ECF6E)
                        NetworkStatus.DOWN -> Color(0xFFE5484D)
                        NetworkStatus.CHECKING -> NextendoTextSecondary
                    }
                    val dotColor by animateColorAsState(
                        targetValue = targetDotColor,
                        animationSpec = tween(300),
                        label = "networkDotColor"
                    )

                    val pulseTransition = rememberInfiniteTransition(label = "networkDotPulse")
                    val pulseAlpha by pulseTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = if (networkStatus == NetworkStatus.CHECKING) 0.35f else 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(700),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "networkDotPulseAlpha"
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(dotColor.copy(alpha = pulseAlpha))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    AnimatedContent(
                        targetState = networkStatus,
                        transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(150)) },
                        label = "networkStatusText"
                    ) { status ->
                        val text = when (status) {
                            NetworkStatus.OPERATIONAL -> Strings.networkOperational(appLanguage)
                            NetworkStatus.DOWN -> Strings.networkDown(appLanguage)
                            NetworkStatus.CHECKING -> Strings.networkChecking(appLanguage)
                        }
                        Text(
                            text = text,
                            color = NextendoTextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Sessions row
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .clickable { onSessionsClick() }
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Devices,
                        contentDescription = null,
                        tint = NextendoTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = Strings.mySessions(appLanguage), color = NextendoTextPrimary, fontSize = 15.sp)
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = NextendoTextSecondary
                )
            }
        }

        // Logout Action Button
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(containerColor = NextendoSurfaceElevated),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = NextendoPink,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Strings.logout(appLanguage),
                        color = NextendoPink,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }

        // Delete Account Action
        item {
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(
                onClick = { showDeleteAccountDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = Strings.deleteAccountButton(appLanguage),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    if (showChangeEmailDialog) {
        var newEmail by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showChangeEmailDialog = false },
            title = { Text(text = Strings.changeEmailTitle(appLanguage)) },
            text = {
                Column {
                    OutlinedTextField(
                        value = newEmail,
                        onValueChange = { newEmail = it },
                        label = { Text(Strings.newEmailLabel(appLanguage)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(Strings.currentPasswordLabel(appLanguage)) },
                        singleLine = true,
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onChangeEmailClick(newEmail.trim(), password) { _, message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                        showChangeEmailDialog = false
                    },
                    enabled = newEmail.contains("@") && password.isNotBlank()
                ) { Text(text = Strings.saveButton(appLanguage)) }
            },
            dismissButton = {
                TextButton(onClick = { showChangeEmailDialog = false }) { Text(text = Strings.cancelButton(appLanguage)) }
            }
        )
    }

    if (showDeleteAccountDialog) {
        var password by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showDeleteAccountDialog = false },
            title = { Text(text = Strings.deleteAccountConfirmTitle(appLanguage)) },
            text = {
                Column {
                    Text(text = Strings.deleteAccountConfirmDesc(appLanguage), fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(Strings.passwordLabel(appLanguage)) },
                        singleLine = true,
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteAccountClick(password) { _, message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                        showDeleteAccountDialog = false
                    },
                    enabled = password.isNotBlank()
                ) { Text(text = Strings.deleteAccountButton(appLanguage), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAccountDialog = false }) { Text(text = Strings.cancelButton(appLanguage)) }
            }
        )
    }
}
