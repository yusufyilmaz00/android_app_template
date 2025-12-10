package com.unluckyprayers.associumhub.ui.screen.club

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Campaign
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.ui.graphics.Color
import com.unluckyprayers.associumhub.domain.model.club.Club
import com.unluckyprayers.associumhub.ui.theme.CardBackground
import com.unluckyprayers.associumhub.ui.theme.DarkBackground
import com.unluckyprayers.associumhub.ui.theme.NeonGreen
import com.unluckyprayers.associumhub.ui.theme.Slate300
import com.unluckyprayers.associumhub.ui.theme.Slate400

@Composable
fun ClubDetailUI(
    club: Club, // Parametreyi ClubDetail olarak değiştir
     onNavigateBack: () -> Unit,
     onMoreOptionsClick: () -> Unit
) {
    // Scroll state tüm sayfa için
    val scrollState = rememberScrollState()

    // Tab seçimi için state (0: About, 1: Gallery, 2: Events, 3: Announcements)
    var selectedTabIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground) // Starry-bg temeli
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // 1. Üst Kısım (Cover + Logo)
            ClubHeroSection(
                coverUrl = club.coverUrl,
                logoUrl = club.logoUrl
            )

            // 2. Bilgi Kartı (Başlık, Açıklama, Butonlar)
            // Logo cover'dan taştığı için biraz boşluk bırakıyoruz
            Spacer(modifier = Modifier.height(40.dp))

            ClubInfoCard(
                name = club.name,
                description = club.shortDescription,
                foundedYear = club.foundedYear
            )

            // 3. Sektör Etiketleri (Yatay Liste)
            ClubSectorChips(sectors = club.sectors)

            // 4. Sekmeler (Tabs)
            ClubTabs(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            // 5. Değişen İçerik Alanı
            Box(modifier = Modifier.padding(16.dp)) {
                when (selectedTabIndex) {
                    0 -> AboutTabContent(aboutText = club.aboutText, email = club.email)
                    1 -> PlaceholderTabContent(icon = "photo_library", message = "No photos yet.")
                    2 -> PlaceholderTabContent(icon = "event", message = "No upcoming events.")
                    3 -> PlaceholderTabContent(icon = "campaign", message = "No announcements.")
                }
            }

            // Alt boşluk
            Spacer(modifier = Modifier.height(20.dp))
        }

        // En üst katman: Geri ve Menü butonları (Sayfa kayarken sabit veya üstte kalabilir)
        ClubTopNavigation(
            onBackClick = { onNavigateBack() },
            onMenuClick = { onMoreOptionsClick() }
        )
    }
}


@Composable
fun ClubHeroSection(coverUrl: String?, logoUrl: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp) // min-h-[280px]
    ) {
        // Cover Resmi
        Image(
            painter = rememberAsyncImagePainter(coverUrl ?: "https://via.placeholder.com/800"),
            contentDescription = "Cover Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient Overlay (CSS: bg-gradient-to-t from-black/60)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomStart)
                .offset(x = 24.dp, y = 40.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .border(4.dp, DarkBackground, RoundedCornerShape(12.dp)) // border-[#0d1a2e]
                .background(Color.White)
        ) {
            Image(
                painter = rememberAsyncImagePainter(logoUrl ?: "https://via.placeholder.com/150"),
                contentDescription = "Club Logo",
                contentScale = ContentScale.Fit, //Crop
                modifier = Modifier.fillMaxSize().padding(2.dp)
            )
            // Neon Glow effect (Basitleştirilmiş border)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, NeonGreen.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            )
        }
    }
}

@Composable
fun ClubInfoCard(name: String, description: String?, foundedYear: Int?) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = CardBackground.copy(alpha = 0.65f), // Backdrop blur taklidi
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Slate300
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (foundedYear != null) {
            Text(
                text = "Est. $foundedYear",
                style = MaterialTheme.typography.labelMedium,
                color = Slate400
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Butonlar Satırı
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Follow Button
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFECDD3)), // rose-200
                modifier = Modifier.weight(1f).height(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Follow", color = Color(0xFFDC2626), style = MaterialTheme.typography.labelLarge) // red-600
            }

            // Join Button
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                modifier = Modifier.weight(1f).height(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Groups, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Join", color = Color.Black, style = MaterialTheme.typography.labelLarge)
            }

            // Notification Button
            FilledIconButton(
                onClick = {},
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFF1E304B)),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.Notifications, contentDescription = null, tint = Slate300)
            }
        }
    }
}

@Composable
fun ClubSectorChips(sectors: List<String>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        items(sectors) { sector ->
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(NeonGreen.copy(alpha = 0.2f), RoundedCornerShape(50)) // primary/20
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = sector,
                    color = Color(0xFFE2E8F0), // slate-200
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun ClubTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("About", "Gallery", "Events", "Announcements")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.5.dp, color = Color.White.copy(alpha = 0.12f)) // Alt çizgi
            .padding(top = 12.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTabIndex == index
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(index) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                    style = if (isSelected) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Aktif sekme altındaki yeşil çizgi
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .background(NeonGreen, RoundedCornerShape(50))
                    )
                } else {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun AboutTabContent(aboutText: String?, email: String?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "About Our Club",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = aboutText ?: "No information provided.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.9f),
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
        )

        if (email != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color(0xFFFBBF24), // amber-400
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = email,
                    color = Color(0xFFFBBF24),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun PlaceholderTabContent(icon: String, message: String) {

    val imageVector = when(icon) {
        "photo_library" -> Icons.Rounded.PhotoLibrary
        "event" -> Icons.Rounded.Event
        "campaign" -> Icons.Rounded.Campaign
        else -> Icons.Default.Info
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Slate400,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message, color = Slate400)
    }
}

@Composable
fun ClubTopNavigation(onBackClick: () -> Unit, onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.3f), androidx.compose.foundation.shape.CircleShape)
                .size(40.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.3f), androidx.compose.foundation.shape.CircleShape)
                .size(40.dp)
        ) {
            Icon(Icons.Default.MoreHoriz, contentDescription = "Menu", tint = Color.White)
        }
    }
}