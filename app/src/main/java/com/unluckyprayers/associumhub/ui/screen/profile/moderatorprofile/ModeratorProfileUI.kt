package com.unluckyprayers.associumhub.ui.screen.profile.moderatorprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unluckyprayers.associumhub.ui.theme.CardBorderColor
import com.unluckyprayers.associumhub.ui.theme.DarkBackground
import com.unluckyprayers.associumhub.ui.theme.GlassPanelColor
import com.unluckyprayers.associumhub.ui.theme.NeonGreen


@Composable
fun ModeratorProfileUI(
    onBackClick : () -> Unit = {},
    onMoreOptionsClick : () -> Unit = {}
) {
    // Sayfanın genel yapısı
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground) // Starry-bg yerine düz dark zemin
    ) {
        // İçerik Kaydırma Alanı
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            // 1. Header & Cover Kısmı
            ProfileHeaderSection()

            // 2. İstatistikler
            StatsSection()

            // 3. Etiketler (Tags)
            TagsSection()

            // 4. Tab Menü (About / Gallery)
            TabSelectionSection()

            // 5. İçerik (About Kısmı)
            AboutContentSection()
        }

        // Top Navigation (Sabit Üst Bar)
        TopNavigationOverlay()
    }
}

@Composable
fun TopNavigationOverlay() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Sistem barının altında başlasın
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Geri Butonu
        IconButton(
            onClick = { /* TODO: Navigate Back */ },
            modifier = Modifier
                .size(40.dp)
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Seçenekler Butonu
        IconButton(
            onClick = { /* TODO: Open Menu */ },
            modifier = Modifier
                .size(40.dp)
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ProfileHeaderSection() {
    Box(modifier = Modifier.fillMaxWidth()) {
        // Cover Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.DarkGray) // Placeholder renk
        ) {
            // Burada gerçek resim olacak. Örnek gradient overlay:
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, DarkBackground),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }

        // Profil Kartı (Cover'ın üzerine binen kısım)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 180.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Glass Panel Efekti
            Surface(
                color = GlassPanelColor,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp, bottomStart = 40.dp, bottomEnd = 40.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 40.dp), // Avatar için boşluk
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // İsim ve Slogan
                    Text(
                        text = "Tech Innovators Society",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Building the future, one circuit at a time.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Avatar (En üst katman, ortalanmış)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 130.dp) // Cover ile Kart arasına yerleşim
                .size(110.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(NeonGreen, Color.Transparent)
                    ),
                    shape = CircleShape
                )
                .padding(3.dp) // Gradient border kalınlığı
        ) {
            // Avatar Resmi
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder İkon (Resim yerine)
                Icon(
                    imageVector = Icons.Default.Groups, // Kulüp ikonu
                    contentDescription = "Club Logo",
                    tint = NeonGreen,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Composable
fun StatsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatItem(value = "128", label = "MEMBERS")
        StatItem(value = "12", label = "EVENTS")
        StatItem(value = "2023", label = "EST.")
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = NeonGreen
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.5f),
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun TagsSection() {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        PaddingValues(horizontal = 16.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TAGS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.9f)
            )
            // Edit butonu kaldırıldı
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Yatay Kaydırılabilir Tag Listesi
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val tags = listOf("Engineering", "Robotics", "Coding", "AI & ML", "Hardware")
            tags.forEach { tag ->
                TagChip(text = tag)
            }
        }
    }
}

@Composable
fun TagChip(text: String) {
    Surface(
        color = Color.White.copy(alpha = 0.1f),
        shape = RoundedCornerShape(100), // Tam yuvarlak
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Composable
fun TabSelectionSection() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = GlassPanelColor,
        shape = RoundedCornerShape(50),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor)
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            // Aktif Tab (About)
            Button(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "About", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            // Pasif Tab (Gallery)
            TextButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Gallery", color = Color.White.copy(alpha = 0.7f), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AboutContentSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        // Açıklama Kartı
        Surface(
            color = GlassPanelColor,
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "About Our Club",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "We are a community of passionate students dedicated to exploring the frontiers of technology. From robotics workshops to hackathons, we provide the resources and mentorship needed to turn ambitious ideas into reality. Join us to learn, build, and innovate together.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(24.dp))

                // İletişim Bilgileri
                ContactRow(
                    icon = Icons.Outlined.Email,
                    label = "CONTACT EMAIL",
                    value = "contact@techinnovators.edu"
                )
                Spacer(modifier = Modifier.height(16.dp))
                ContactRow(
                    icon = Icons.Outlined.LocationOn,
                    label = "LOCATION",
                    value = "Engineering Hall, Room 304"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sosyal Medya Linkleri
        Surface(
            color = GlassPanelColor,
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Social Links",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SocialIcon(Icons.Outlined.Public)
                    SocialIcon(Icons.Outlined.Share)
                }
            }
        }
    }
}

@Composable
fun ContactRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // İkon Yuvarlağı
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.05f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NeonGreen,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.4f),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SocialIcon(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.White.copy(alpha = 0.05f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.6f),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ModeratorProfilePreview() {
    ModeratorProfileUI()
}