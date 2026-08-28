package com.axolat.nextendroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axolat.nextendroid.data.model.UserSession
import com.axolat.nextendroid.ui.theme.*

@Composable
fun SessionsScreen(
    sessions: List<UserSession>,
    isLoading: Boolean,
    appLanguage: AppLanguage = AppLanguage.FR,
    onBackClick: () -> Unit,
    onRevokeSession: (UserSession) -> Unit,
    onRevokeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    var confirmTarget by remember { mutableStateOf<UserSession?>(null) }
    var confirmAll by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NextendoTextPrimary
                    )
                }
                Text(
                    text = Strings.mySessions(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (isLoading && sessions.isEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NextendoPink)
                }
            }
        } else if (sessions.isEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text(text = Strings.noSessions(appLanguage), color = NextendoTextSecondary, fontSize = 14.sp)
                }
            }
        } else {
            items(sessions.sortedByDescending { it.current }) { session ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(NextendoSurfaceCard)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val icon = when (session.kind) {
                        "ryujinx" -> Icons.Filled.SportsEsports
                        "switch" -> Icons.Filled.PhoneAndroid
                        else -> Icons.Filled.Devices
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(NextendoSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = NextendoPink, modifier = Modifier.size(20.dp))
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = session.kindLabel.ifBlank { session.kind },
                                color = NextendoTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            if (session.current) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(NextendoPink.copy(alpha = 0.15f))
                                        .padding(horizontal = 6.dp, vertical = 1.dp)
                                ) {
                                    Text(text = Strings.thisDeviceLabel(appLanguage), color = NextendoPink, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = listOfNotNull(session.geo, session.ip).joinToString(" · ").ifBlank { session.ip ?: "" },
                            color = NextendoTextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    TextButton(onClick = { confirmTarget = session }) {
                        Text(
                            text = if (session.current) Strings.disconnectThisDevice(appLanguage) else Strings.disconnectDevice(appLanguage),
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { confirmAll = true },
                    colors = ButtonDefaults.buttonColors(containerColor = NextendoSurfaceElevated),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text(
                        text = Strings.disconnectAllDevices(appLanguage),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    confirmTarget?.let { session ->
        AlertDialog(
            onDismissRequest = { confirmTarget = null },
            title = { Text(text = if (session.current) Strings.disconnectThisDevice(appLanguage) else Strings.disconnectDevice(appLanguage)) },
            text = { Text(text = session.kindLabel.ifBlank { session.kind }) },
            confirmButton = {
                TextButton(onClick = {
                    onRevokeSession(session)
                    confirmTarget = null
                }) { Text(text = Strings.disconnectDevice(appLanguage), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmTarget = null }) { Text(text = Strings.cancelButton(appLanguage)) }
            }
        )
    }

    if (confirmAll) {
        AlertDialog(
            onDismissRequest = { confirmAll = false },
            title = { Text(text = Strings.disconnectAllDevices(appLanguage)) },
            confirmButton = {
                TextButton(onClick = {
                    onRevokeAll()
                    confirmAll = false
                }) { Text(text = Strings.disconnectAllDevices(appLanguage), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmAll = false }) { Text(text = Strings.cancelButton(appLanguage)) }
            }
        )
    }
}
