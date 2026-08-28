package com.axolat.nextendroid.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axolat.nextendroid.data.model.FriendRequest
import com.axolat.nextendroid.data.model.GameDictionary
import com.axolat.nextendroid.ui.components.AvatarView
import com.axolat.nextendroid.ui.theme.*

@Composable
fun AddFriendScreen(
    userFriendCode: String,
    incomingRequests: List<FriendRequest> = emptyList(),
    appLanguage: AppLanguage = AppLanguage.FR,
    onBackClick: () -> Unit,
    onSendRequestClick: (String, (Boolean, String) -> Unit) -> Unit,
    onAcceptRequest: (Long) -> Unit = {},
    onDeclineRequest: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var friendCodeInput by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Top Navigation Bar
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
                    text = Strings.addFriendTitle(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Card 1: Your Friend Code
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(16.dp)
            ) {
                Text(
                    text = Strings.yourFriendCode(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Donnez-le à l'autre joueur : il envoie la demande et elle apparaît ci-dessous.",
                    color = NextendoTextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(NextendoDarkBackground)
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = userFriendCode,
                        color = NextendoTextPrimary,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(NextendoSurfaceElevated)
                            .clickable {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Friend Code", userFriendCode)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Code ami copié !", Toast.LENGTH_SHORT).show()
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy",
                            tint = NextendoPink,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = Strings.copyButton(appLanguage),
                            color = NextendoPink,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Card 2: Add by Friend Code Input
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(16.dp)
            ) {
                Text(
                    text = Strings.addByCodeTitle(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Avec ou sans les tirets, cela ne change rien : le serveur accepte les deux.",
                    color = NextendoTextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = friendCodeInput,
                    onValueChange = { friendCodeInput = it },
                    placeholder = { Text("SW-0000-0000-0000", color = NextendoTextMuted) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NextendoDarkBackground,
                        unfocusedContainerColor = NextendoDarkBackground,
                        focusedBorderColor = NextendoPink,
                        unfocusedBorderColor = NextendoDivider,
                        focusedTextColor = NextendoTextPrimary,
                        unfocusedTextColor = NextendoTextPrimary
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (friendCodeInput.isNotBlank() && !isSending) {
                            isSending = true
                            onSendRequestClick(friendCodeInput.trim()) { success, msg ->
                                isSending = false
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                if (success) friendCodeInput = ""
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NextendoPink),
                    shape = RoundedCornerShape(14.dp),
                    enabled = !isSending && friendCodeInput.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isSending) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(18.dp))
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = Strings.sendRequest(appLanguage),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // Section Title: Incoming Requests
        item {
            Text(
                text = Strings.incomingRequests(appLanguage),
                color = NextendoTextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
            )
        }

        // Incoming Requests List or Empty State Card
        if (incomingRequests.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(NextendoSurfaceCard)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Strings.noRequests(appLanguage),
                        color = NextendoTextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            items(incomingRequests) { req ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(NextendoSurfaceCard)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        AvatarView(
                            username = req.name ?: req.username,
                            avatarUrl = GameDictionary.getAvatarUrl(req.image, req.avatar),
                            size = 48.dp,
                            showOnlineDot = false
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = req.name ?: req.username,
                                color = NextendoTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = req.friendCode,
                                color = NextendoTextSecondary,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Row {
                        IconButton(
                            onClick = { onAcceptRequest(req.pid) },
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(NextendoOnlineGreen)
                                .size(36.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "Accept", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { onDeclineRequest(req.pid) },
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(NextendoPink)
                                .size(36.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Decline", tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}
