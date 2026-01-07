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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
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
    // Bugünün başlangıç zamanını hesapla (gece yarısı)
    val today = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    // Date Picker State - sadece bugün ve sonrası seçilebilir
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= today
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year >= Calendar.getInstance().get(Calendar.YEAR)
            }
        }
    )

    // Seçilen tarihi sakla (saat validasyonu için)
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    // Time Picker State
    var showTimePicker by remember { mutableStateOf(false) }
    var timeValidationError by remember { mutableStateOf<String?>(null) }
    val currentCalendar = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentCalendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentCalendar.get(Calendar.MINUTE)
    )

    // Seçilen tarih bugün mü kontrol et
    fun isSelectedDateToday(): Boolean {
        selectedDateMillis?.let { millis ->
            val selectedCal = Calendar.getInstance().apply { timeInMillis = millis }
            val todayCal = Calendar.getInstance()
            return selectedCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) &&
                    selectedCal.get(Calendar.DAY_OF_YEAR) == todayCal.get(Calendar.DAY_OF_YEAR)
        }
        return false
    }

    // Seçilen saat geçerli mi kontrol et
    fun isTimeValid(hour: Int, minute: Int): Boolean {
        if (!isSelectedDateToday()) return true
        val now = Calendar.getInstance()
        val selectedMinutes = hour * 60 + minute
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)
        return selectedMinutes > currentMinutes
    }

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDateMillis = millis
                            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                            val formattedDate = dateFormat.format(Date(millis))
                            onDateChange(formattedDate)
                            // Tarih değiştiğinde saat sıfırlansın
                            onTimeChange("")
                            timeValidationError = null
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK", color = NeonGreen)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = TextWhite.copy(alpha = 0.6f))
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = SheetBackground
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = SheetBackground,
                    titleContentColor = TextWhite,
                    headlineContentColor = TextWhite,
                    weekdayContentColor = TextHint,
                    subheadContentColor = TextWhite,
                    navigationContentColor = TextWhite,
                    yearContentColor = TextWhite,
                    disabledYearContentColor = TextHint.copy(alpha = 0.3f),
                    currentYearContentColor = NeonGreen,
                    selectedYearContentColor = SheetBackground,
                    selectedYearContainerColor = NeonGreen,
                    dayContentColor = TextWhite,
                    disabledDayContentColor = TextHint.copy(alpha = 0.3f),
                    selectedDayContentColor = SheetBackground,
                    selectedDayContainerColor = NeonGreen,
                    todayContentColor = NeonGreen,
                    todayDateBorderColor = NeonGreen
                )
            )
        }
    }

    // Time Picker Dialog
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { 
                showTimePicker = false
                timeValidationError = null
            },
            containerColor = SheetBackground,
            title = {
                Column {
                    Text(
                        text = "Select Time",
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )
                    if (isSelectedDateToday()) {
                        Text(
                            text = "Today - select a future time",
                            color = TextHint,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            },
            text = {
                Column {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            containerColor = SheetBackground,
                            clockDialColor = InputBackground,
                            clockDialSelectedContentColor = SheetBackground,
                            clockDialUnselectedContentColor = TextWhite,
                            selectorColor = NeonGreen,
                            periodSelectorBorderColor = BorderColor,
                            periodSelectorSelectedContainerColor = NeonGreen,
                            periodSelectorUnselectedContainerColor = InputBackground,
                            periodSelectorSelectedContentColor = SheetBackground,
                            periodSelectorUnselectedContentColor = TextWhite,
                            timeSelectorSelectedContainerColor = NeonGreen,
                            timeSelectorUnselectedContainerColor = InputBackground,
                            timeSelectorSelectedContentColor = SheetBackground,
                            timeSelectorUnselectedContentColor = TextWhite
                        )
                    )
                    // Hata mesajı
                    if (timeValidationError != null) {
                        Text(
                            text = timeValidationError!!,
                            color = Color(0xFFEF4444),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        
                        if (isTimeValid(hour, minute)) {
                            val amPm = if (hour >= 12) "PM" else "AM"
                            val hour12 = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour
                            val formattedTime = String.format("%d:%02d %s", hour12, minute, amPm)
                            onTimeChange(formattedTime)
                            showTimePicker = false
                            timeValidationError = null
                        } else {
                            timeValidationError = "Please select a future time"
                        }
                    }
                ) {
                    Text("OK", color = NeonGreen)
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showTimePicker = false
                    timeValidationError = null
                }) {
                    Text("Cancel", color = TextWhite.copy(alpha = 0.6f))
                }
            }
        )
    }

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
                                contentScale = ContentScale.Fit,
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
                            ClickableField(
                                value = uiState.date,
                                placeholder = "Select date",
                                trailingIcon = Icons.Default.CalendarToday,
                                onClick = { showDatePicker = true }
                            )
                        }
                        FormSection(label = "Time", modifier = Modifier.weight(1f)) {
                            ClickableField(
                                value = uiState.time,
                                placeholder = "Select time",
                                trailingIcon = Icons.Default.Schedule,
                                onClick = { showTimePicker = true }
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
fun ClickableField(
    value: String,
    placeholder: String,
    trailingIcon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(InputBackground)
            .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = value.ifEmpty { placeholder },
                color = if (value.isEmpty()) TextHint else TextWhite,
                style = TextStyle(fontSize = 16.sp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            imageVector = trailingIcon,
            contentDescription = null,
            tint = if (value.isEmpty()) TextHint else NeonGreen,
            modifier = Modifier.size(20.dp)
        )
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
