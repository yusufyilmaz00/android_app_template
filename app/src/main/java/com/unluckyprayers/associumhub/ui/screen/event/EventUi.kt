package com.unluckyprayers.associumhub.ui.screen.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.unluckyprayers.associumhub.domain.model.event.EventUiModel
import com.unluckyprayers.associumhub.ui.theme.AssociumBackground
import com.unluckyprayers.associumhub.ui.theme.AssociumError
import com.unluckyprayers.associumhub.ui.theme.AssociumPrimary
import com.unluckyprayers.associumhub.ui.theme.AssociumSurface
import com.unluckyprayers.associumhub.ui.theme.AssociumSurfaceVariant
import com.unluckyprayers.associumhub.ui.theme.CardBorderColor
import com.unluckyprayers.associumhub.ui.theme.TextPrimary
import com.unluckyprayers.associumhub.ui.theme.TextSecondary


@Composable
fun EventUi(
    uiState: EventUiState,
    onBackClick: () -> Unit = {},
    onAddEventClick: () -> Unit = {},
    onQRClick: (String, String, String) -> Unit = { _, _, _ -> }
) {
    Scaffold(
        containerColor = AssociumBackground,
        topBar = {
            EventTopBar(
                onBackClick = onBackClick,
                onAddEventClick = onAddEventClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AssociumPrimary
                    )
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = AssociumError,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.events.isEmpty() -> {
                    Text(
                        text = "Henüz etkinlik bulunmamaktadır",
                        color = TextSecondary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
                    ) {
                        items(uiState.events) { event ->
                            EventCard(
                                event = event,
                                onQRClick = { onQRClick(event.id, event.title, event.imageUrl) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventTopBar(
    onBackClick: () -> Unit,
    onAddEventClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Geri Butonu
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.05f))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Başlık
        Text(
            text = "Events",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(start = 12.dp).weight(1f)
        )

        // Ekleme Butonu (Kırmızı)
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(AssociumError)
                .clickable { onAddEventClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create Event",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Composable
fun EventCard(
    event: EventUiModel,
    onQRClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AssociumSurface),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .border(1.dp, CardBorderColor, RoundedCornerShape(16.dp))
            .clickable { /* Detay sayfasına git */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- COIL IMAGE BÖLÜMÜ ---
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.imageUrl)
                    .crossfade(true) // Yumuşak geçiş efekti
                    .build(),
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop, // Resmi alana doldur ve kırp
                modifier = Modifier
                    .width(86.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AssociumSurfaceVariant) // Resim yüklenirken arkada görünecek renk
            )

            Spacer(modifier = Modifier.width(16.dp))

            // --- METİN BÖLÜMÜ ---
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = event.clubName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AssociumPrimary,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.date,
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }
            }
            
            // --- QR BUTONU ---
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(
                onClick = onQRClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = "QR Code",
                    tint = AssociumPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}