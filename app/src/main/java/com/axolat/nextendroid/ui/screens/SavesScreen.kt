package com.axolat.nextendroid.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.axolat.nextendroid.data.model.CloudSaveItem
import com.axolat.nextendroid.data.model.GameDictionary
import com.axolat.nextendroid.data.model.SavesResponse
import com.axolat.nextendroid.ui.theme.*
import java.util.Locale

@Composable
fun SavesScreen(
    savesResponse: SavesResponse?,
    appLanguage: AppLanguage = AppLanguage.FR,
    onDeleteSave: (titleId: String, onResult: (Boolean) -> Unit) -> Unit = { _, _ -> },
    onDownloadSave: (titleId: String, fileName: String, onResult: (Boolean, String) -> Unit) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var deleteTarget by remember { mutableStateOf<CloudSaveItem?>(null) }
    val totalSize = savesResponse?.totalSize ?: 1363264L // 1.3 MB
    val limit = savesResponse?.limit ?: 10485760L // 10.0 MB
    val isBooster = savesResponse?.isBooster ?: true
    val eligible = savesResponse?.eligible ?: true
    val reasonCode = savesResponse?.reasonCode
    val saves = savesResponse?.saves ?: emptyList()

    val totalMb = totalSize / (1024f * 1024f)
    val limitMb = limit / (1024f * 1024f)
    val remainingMb = (limit - totalSize).coerceAtLeast(0) / (1024f * 1024f)
    val progress = (totalSize.toFloat() / limit.toFloat()).coerceIn(0f, 1f)

    val formattedUsedMb = String.format(Locale.US, "%.1f", totalMb)
    val formattedLimitMb = String.format(Locale.US, "%.1f", limitMb)
    val formattedRemainingMb = String.format(Locale.US, "%.1f", remainingMb)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Header Title
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Strings.tabSaves(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        // Eligibility Gate: cloud saves require a verified email AND a linked Discord account
        if (!eligible) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(NextendoSurfaceCard)
                        .padding(20.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Filled.CloudOff,
                        contentDescription = null,
                        tint = NextendoPink,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = if (reasonCode == "discord") {
                            Strings.saveNotEligibleDiscord(appLanguage)
                        } else {
                            Strings.saveNotEligibleEmail(appLanguage)
                        },
                        color = NextendoTextSecondary,
                        fontSize = 13.sp
                    )
                }
            }
            return@LazyColumn
        }

        // Storage Progress Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Strings.storageUsed(appLanguage),
                        color = NextendoTextSecondary,
                        fontSize = 14.sp
                    )

                    if (isBooster) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = NextendoBoosterPink,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "BOOSTER",
                                color = NextendoBoosterPink,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$formattedUsedMb Mo",
                        color = NextendoTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                    Text(
                        text = " sur $formattedLimitMb Mo",
                        color = NextendoTextSecondary,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = NextendoPink,
                    trackColor = NextendoSurfaceElevated
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = Strings.remaining(appLanguage, formattedRemainingMb),
                    color = NextendoTextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // Info Banner
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(NextendoSurfaceCard)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = NextendoPink,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = Strings.readOnlyTitle(appLanguage),
                        color = NextendoTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = Strings.readOnlyDesc(appLanguage),
                        color = NextendoTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Section Title: Games Count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${saves.size} ${Strings.tabSaves(appLanguage).uppercase()}",
                    color = NextendoTextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Saves List
        if (saves.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Strings.noSaves(appLanguage),
                        color = NextendoTextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            items(saves) { item ->
                val sizeKb = item.size / 1024
                val coverUrl = GameDictionary.getGameCoverUrl(item.titleId)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(NextendoSurfaceCard)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(NextendoSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        if (!coverUrl.isNullOrEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(coverUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = item.resolvedName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.CloudDone,
                                contentDescription = null,
                                tint = NextendoPink,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.resolvedName,
                            color = NextendoTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.updatedAt ?: "Récemment synchronisé",
                            color = NextendoTextMuted,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }

                    Text(
                        text = "$sizeKb Ko",
                        color = NextendoTextSecondary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )

                    IconButton(
                        onClick = {
                            onDownloadSave(item.titleId, "${item.resolvedName}.zip") { success, message ->
                                val text = if (success) Strings.saveDownloadedTo(appLanguage, message) else message
                                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Download,
                            contentDescription = Strings.downloadSaveAction(appLanguage),
                            tint = NextendoTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = { deleteTarget = item },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = Strings.deleteSaveAction(appLanguage),
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    deleteTarget?.let { item ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            title = { Text(text = Strings.deleteSaveAction(appLanguage)) },
            text = { Text(text = Strings.confirmDeleteSaveDesc(appLanguage)) },
            confirmButton = {
                TextButton(onClick = {
                    deleteTarget = null
                    onDeleteSave(item.titleId) { success ->
                        if (!success) Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show()
                    }
                }) { Text(text = Strings.deleteSaveAction(appLanguage), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { deleteTarget = null }) { Text(text = Strings.cancelButton(appLanguage)) }
            }
        )
    }
}
