package com.axolat.nextendroid.ui.screens

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.scale
import com.axolat.nextendroid.data.model.CountryUtils
import com.axolat.nextendroid.data.model.GameDictionary
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
    modifier: Modifier = Modifier,
    onSaveUsernameClick: (String) -> Unit = {},
    onSaveCountryClick: (String) -> Unit = {},
    onSaveProfileImageClick: (String) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var isEditingUsername by remember { mutableStateOf(false) }
    var usernameInput by remember { mutableStateOf(userAccount?.displayUsername ?: "Axolat") }
    var countryDropdownExpanded by remember { mutableStateOf(false) }

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
                        Text(
                            text = userAccount?.email ?: "axolat@nextendo.network",
                            color = NextendoTextSecondary,
                            fontSize = 14.sp
                        )
                        if (userAccount?.emailVerified == true) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "✓", color = NextendoPink, fontSize = 14.sp, fontWeight = FontWeight.Bold)
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

        // Language Options Selector Card (Lists ALL 7 Supported Languages)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
            ) {
                val languages = AppLanguage.entries
                languages.forEachIndexed { index, lang ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageChange(lang) }
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${lang.flag} ${lang.displayName}",
                            color = NextendoTextPrimary,
                            fontSize = 15.sp,
                            fontWeight = if (appLanguage == lang) FontWeight.Bold else FontWeight.Normal
                        )
                        if (appLanguage == lang) {
                            Text(
                                text = "✓",
                                color = NextendoPink,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    if (index < languages.size - 1) {
                        HorizontalDivider(color = NextendoDivider, modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }

        // Logout Action Button
        item {
            Spacer(modifier = Modifier.height(28.dp))
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
    }
}
