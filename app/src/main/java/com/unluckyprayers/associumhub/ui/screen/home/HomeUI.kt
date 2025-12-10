package com.unluckyprayers.associumhub.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.ui.components.StarrySkyBackground
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.unluckyprayers.associumhub.R
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeUI(
    modifier: Modifier = Modifier,
    clubs: LazyPagingItems<ClubItem>,
    onClubClick: (Int) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // 1) Arka Plan (Yıldızlı Gökyüzü)
        StarrySkyBackground(modifier = Modifier.matchParentSize())

        // 2) Hafif Overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0.25f)
        )

        // 3) Ana İçerik
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Başlık
            Text(
                text = "Explore Clubs",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Arama Çubuğu
            ClubSearchBar(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            // İlk yükleme (Tam sayfa loading)
            if (clubs.loadState.refresh is LoadState.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            // İlk yükleme hatası
            else if (clubs.loadState.refresh is LoadState.Error) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Bir hata oluştu.", color = Color.White)
                }
            }
            // Liste Yüklendiğinde
            else {
                ClubsPagingGrid(
                    clubs = clubs,
                    modifier = Modifier.weight(1f),
                    onClubClick = onClubClick
                )
            }
        }
    }
}

@Composable
fun ClubSearchBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .height(56.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Search for clubs...",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}

/**
 * Pagination Destekli Grid Yapısı
 */
@Composable
fun ClubsPagingGrid(
    clubs: LazyPagingItems<ClubItem>,
    modifier: Modifier = Modifier,
    onClubClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- 1. Veri Listeleme ---
        items(clubs.itemCount) { index ->
            val club = clubs[index]
            if (club != null) {
                ClubCard(clubItem = club, onClick = { onClubClick(club.id) })
            }
        }

        // --- 2. Pagination Loading & Error (Footer) ---
        // Kullanıcı listenin sonuna geldiğinde çalışır
        when (val appendState = clubs.loadState.append) {
            is LoadState.Loading -> {
                item(span = { GridItemSpan(3) }) { // 3 sütunu da kapla
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }
            is LoadState.Error -> {
                item(span = { GridItemSpan(3) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Burada normalde bir "Tekrar Dene" butonu konulur
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error",
                            tint = Color.Red
                        )
                    }
                }
            }
            is LoadState.NotLoading -> Unit
        }
    }
}

@Composable
fun ClubCard(
    clubItem: ClubItem,
    onClick: () -> Unit
) {
    val defaultLogoUrl = "https://pbginizanmtsllleimim.supabase.co/storage/v1/object/public/club-assets/logos/associumhub_default_logo.png"

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick)

    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // LOGO
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        // 1. URL boş veya null ise varsayılan URL'yi kullan
                        .data(clubItem.logoUrl.takeIf { !it.isNullOrBlank() } ?: defaultLogoUrl)
                        .crossfade(true)
                        // 2. Hata durumunda da varsayılan URL'yi kullan
                        .error(R.drawable.associumhub_default_cover)
                        .build(),
                    contentDescription = "${clubItem.name} logo",
                    // placeholder'ı yine de tutmak iyi bir pratiktir
                    placeholder = rememberVectorPainter(Icons.Default.ImageNotSupported),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                // Yıl Etiketi
                Surface(
                    shape = RoundedCornerShape(bottomStart = 8.dp),
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = clubItem.foundedYear.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // İsim
            Text(
                text = clubItem.name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                ),
                color = Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF0D1117,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun HomeUIPreview() {
    val mockClubItems = listOf(
        ClubItem(1, "Debate Team", 2010, ""), // URL boş, placeholder görünecek
        ClubItem(2, "Coding Club", 2015, ""),
        ClubItem(3, "Photography", 2018, ""),
        ClubItem(4, "Art & Design", 2008, ""),
        ClubItem(5, "Book Club", 2012, ""),
        ClubItem(6, "Music Soc.", 2016, "")
    )
    val flow = remember {
        flowOf(PagingData.from(mockClubItems))
    }

    val lazyPagingItems = flow.collectAsLazyPagingItems()

    MaterialTheme {
        HomeUI(
            clubs = lazyPagingItems,
            onClubClick = {}
        )
    }
}