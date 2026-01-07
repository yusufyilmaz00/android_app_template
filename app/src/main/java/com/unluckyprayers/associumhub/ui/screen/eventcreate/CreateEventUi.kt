package com.unluckyprayers.associumhub.ui.screen.eventcreate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.unluckyprayers.associumhub.ui.theme.BorderColor
import com.unluckyprayers.associumhub.ui.theme.InputBackground
import com.unluckyprayers.associumhub.ui.theme.NeonGreen
import com.unluckyprayers.associumhub.ui.theme.SheetBackground
import com.unluckyprayers.associumhub.ui.theme.TextHint
import com.unluckyprayers.associumhub.ui.theme.TextWhite

@Composable
fun CreateEventUi(
    uiState: CreateEventUiState, // State verisini buradan alıyoruz
    onTitleChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageClick: () -> Unit, // Resim seçme tetikleyicisi
    onCancelClick: () -> Unit = {},
    onPostClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SheetBackground,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        // Ekranın tamamını kapsayan Box (Loading overlay için gerekli)
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                // 1. DRAG HANDLE
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .align(Alignment.CenterHorizontally)
                )

                // 2. HEADER
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onCancelClick) {
                        Text(
                            text = "Cancel",
                            color = TextWhite.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Text(
                        text = "New Event",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextWhite
                    )

                    Button(
                        onClick = onPostClick,
                        // Loading ise veya form geçersizse butonu pasif yapabilirsin
                        enabled = !uiState.isLoading && uiState.isFormValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonGreen,
                            contentColor = SheetBackground,
                            disabledContainerColor = NeonGreen.copy(alpha = 0.3f),
                            disabledContentColor = SheetBackground.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = SheetBackground,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Post",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }

                // 3. SCROLLABLE CONTENT
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 32.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // --- UPLOAD COVER IMAGE (GÜNCELLENDİ) ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(InputBackground)
                            .dashedBorder(2.dp, BorderColor, 16.dp)
                            .clickable { onImageClick() }, // Tıklama işlevi
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.selectedImageUri != null) {
                            // Resim Seçilmişse Göster (Coil)
                            AsyncImage(
                                model = uiState.selectedImageUri,
                                contentDescription = "Selected Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            // Resmin üzerine hafif bir karartma ve ikon koyalım ki değiştirilebileceği anlaşılsın
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = "Change Image",
                                    tint = TextWhite.copy(alpha = 0.8f)
                                )
                            }
                        } else {
                            // Resim Yoksa Placeholder Göster
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(Color.White.copy(alpha = 0.05f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddAPhoto,
                                        contentDescription = null,
                                        tint = NeonGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Upload Cover Image",
                                    color = TextWhite,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Tap to select from gallery",
                                    color = TextHint,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- FORM FIELDS (VERİ BAĞLANTILARI) ---

                    // Event Title
                    FormSection(label = "Event Title") {
                        CustomTextField(
                            value = uiState.title, // State'den gelen değer
                            onValueChange = onTitleChange, // ViewModel fonksiyonu
                            placeholder = "Name your event"
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Date & Time Row
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        FormSection(label = "Date", modifier = Modifier.weight(1f)) {
                            CustomTextField(
                                value = uiState.date,
                                onValueChange = onDateChange,
                                placeholder = "Oct 24, 2023",
                                trailingIcon = Icons.Default.CalendarToday
                            )
                        }
                        FormSection(label = "Time", modifier = Modifier.weight(1f)) {
                            CustomTextField(
                                value = uiState.time,
                                onValueChange = onTimeChange,
                                placeholder = "8:00 PM",
                                trailingIcon = Icons.Default.Schedule
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Location
                    FormSection(label = "Location") {
                        CustomTextField(
                            value = uiState.location,
                            onValueChange = onLocationChange,
                            placeholder = "Add location",
                            leadingIcon = Icons.Default.LocationOn,
                            leadingIconTint = NeonGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Description
                    FormSection(label = "Description") {
                        CustomTextField(
                            value = uiState.description,
                            onValueChange = onDescriptionChange,
                            placeholder = "What's this event about?",
                            singleLine = false,
                            minLines = 4
                        )
                    }

                    // Hata Mesajı Gösterimi
                    if (uiState.errorMessage != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = uiState.errorMessage,
                            color = Color(0xFFEF4444),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }

            // Opsiyonel: Tam ekran loading overlay (İstersen açabilirsin)
            /* if (uiState.isLoading) {
                 Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(enabled = false) {}, // Tıklamayı engelle
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = NeonGreen)
                }
            }
            */
        }
    }
}

// --- YARDIMCI COMPOSABLES ---

@Composable
fun FormSection(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = TextWhite.copy(alpha = 0.8f),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        content()
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    leadingIconTint: Color = TextHint,
    trailingIcon: ImageVector? = null,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = TextWhite,
            fontSize = 16.sp,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
        ),
        singleLine = singleLine,
        minLines = minLines,
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(InputBackground)
                    .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = if (minLines > 1) Alignment.Top else Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = leadingIconTint,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = TextHint,
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                    innerTextField()
                }

                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = TextHint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    )
}

fun Modifier.dashedBorder(width: Dp, color: Color, cornerRadius: Dp) = drawBehind {
    drawRoundRect(
        color = color,
        style = Stroke(
            width = width.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
        ),
        cornerRadius = CornerRadius(cornerRadius.toPx())
    )
}
