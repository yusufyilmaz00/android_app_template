package com.unluckyprayers.associumhub.ui.screen.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.domain.model.SectorUiModel
import com.unluckyprayers.associumhub.ui.screen.home.ClubCard
import com.unluckyprayers.associumhub.ui.theme.DarkBackground
import com.unluckyprayers.associumhub.ui.theme.NeonGreen

@Composable
fun SearchUI(
    state: SearchUIState,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onFilterButtonClick: () -> Unit,
    onSectorSelect: (Int) -> Unit,
    onResultClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        StarryNightBackgroundEffect()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            SearchHeaderSection(
                query = state.searchQuery,
                onQueryChange = onSearchQueryChange,
                onClearClick = onClearSearch,
                onFilterClick = onFilterButtonClick,
                sectors = state.sectors,
                onSectorSelect = onSectorSelect
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(
                            color = NeonGreen,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    state.searchResults.isEmpty() && state.searchQuery.isNotEmpty() -> {
                        EmptySearchState(modifier = Modifier.align(Alignment.Center))
                    }
                    else -> {
                        SearchResultsGrid(
                            results = state.searchResults,
                            onResultClick = onResultClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StarryNightBackgroundEffect() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D1A2E),
                        DarkBackground,
                        Color(0xFF062A1F)
                    )
                )
            )
            .alpha(0.8f)
    )
}

@Composable
fun SearchHeaderSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onFilterClick: () -> Unit,
    sectors: List<SectorUiModel>,
    onSectorSelect: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .shadow(12.dp, RoundedCornerShape(12.dp), spotColor = Color.Black.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (query.isEmpty()) {
                            Text("Search clubs...", color = Color.Gray)
                        }
                        BasicTextField(
                            value = query,
                            onValueChange = onQueryChange,
                            textStyle = TextStyle(
                                color = Color.Gray.copy(alpha = 0.9f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            cursorBrush = SolidColor(NeonGreen),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (query.isNotEmpty()) {
                        IconButton(onClick = onClearClick) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Clear Search",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(12.dp, RoundedCornerShape(12.dp), spotColor = Color.Black.copy(alpha = 0.3f))
                    .clickable(onClick = onFilterClick),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter",
                        tint = NeonGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sectors, key = { it.id }) { sector ->
                SectorChip(item = sector, onClick = { onSectorSelect(sector.id) })
            }
        }
    }
}

@Composable
fun SectorChip(
    item: SectorUiModel,
    onClick: () -> Unit
) {
    val backgroundColor = if (item.isSelected) NeonGreen else Color.Black.copy(alpha = 0.4f)
    val textColor = if (item.isSelected) Color.Black else Color.White
    val borderColor = if (item.isSelected) NeonGreen else Color.White.copy(alpha = 0.3f)
    val shadowModifier = if (item.isSelected) {
        Modifier.shadow(15.dp, CircleShape, spotColor = NeonGreen.copy(alpha = 0.5f))
    } else {
        Modifier
    }

    Surface(
        modifier = Modifier
            .height(36.dp)
            .then(shadowModifier)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = backgroundColor,
        border = BorderStroke(if (item.isSelected) 0.dp else 1.5.dp, borderColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = item.name,
                color = textColor,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (item.isSelected) FontWeight.Bold else FontWeight.Medium
                )
            )

            if (item.isSelected) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Deselect",
                    tint = textColor,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun SearchResultsGrid(
    results: List<ClubItem>,
    onResultClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(results, key = { it.id }) { result ->
            ClubCard(
                clubItem = result,
                onClick = { onResultClick(result.id) }
            )
        }
    }
}

@Composable
fun EmptySearchState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.SearchOff,
            contentDescription = "No Results",
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No results found",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Try adjusting your search or filters.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050b14)
@Composable
fun SearchUIPreview() {
    val mockSectors = listOf(
        SectorUiModel(1, "Technology", isSelected = true),
        SectorUiModel(2, "Design", isSelected = false),
        SectorUiModel(3, "Engineering", isSelected = false),
        SectorUiModel(4, "Business", isSelected = false)
    )

    val mockResults = listOf(
        ClubItem(1, "Robotics Club", 2019, ""),
        ClubItem(2, "AI Society", 2021, ""),
        ClubItem(3, "Dev Team", 2023, ""),
        ClubItem(4, "VR Lab", 2020, "")
    )

    var previewState by remember {
        mutableStateOf(
            SearchUIState(
                searchQuery = "Tech",
                sectors = mockSectors,
                searchResults = mockResults
            )
        )
    }

    MaterialTheme {
        SearchUI(
            state = previewState,
            onSearchQueryChange = { previewState = previewState.copy(searchQuery = it) },
            onClearSearch = { previewState = previewState.copy(searchQuery = "") },
            onFilterButtonClick = {},
            onSectorSelect = { id ->
                val updatedSectors = previewState.sectors.map {
                    it.copy(isSelected = it.id == id)
                }
                previewState = previewState.copy(sectors = updatedSectors)
            },
            onResultClick = {}
        )
    }
}