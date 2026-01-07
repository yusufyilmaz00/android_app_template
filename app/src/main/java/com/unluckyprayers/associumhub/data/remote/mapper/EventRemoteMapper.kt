package com.unluckyprayers.associumhub.data.remote.mapper

import com.unluckyprayers.associumhub.data.remote.dto.event.ClubEventDto
import com.unluckyprayers.associumhub.data.remote.dto.event.SearchEventDto
import com.unluckyprayers.associumhub.domain.model.event.EventUiModel
import java.text.SimpleDateFormat
import java.util.Locale

fun ClubEventDto.toDomain(): EventUiModel {
    // Tarih formatını dönüştür: "2024-11-15" -> "Nov 15 • 7:00 PM"
    val formattedDate = formatEventDate(eventDate, eventTime)
    
    return EventUiModel(
        id = id ?: "",
        title = title ?: "",
        clubName = clubName ?: "",
        date = formattedDate,
        imageUrl = imageUrl ?: ""
    )
}

fun SearchEventDto.toDomain(): EventUiModel {
    // Tarih formatını dönüştür: "2024-05-20" -> "May 20"
    val formattedDate = formatEventDateOnly(eventDate)
    
    return EventUiModel(
        id = id,
        title = title,
        clubName = "", // SearchEventDto'da clubName yok, boş bırakıyoruz
        date = formattedDate,
        imageUrl = imageUrl ?: ""
    )
}

private fun formatEventDateOnly(dateStr: String?): String {
    return try {
        val date = dateStr ?: return ""
        
        // Parse date: "2024-05-20"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = dateFormat.parse(date) ?: return date
        
        // Format date: "May 20"
        val displayDateFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        displayDateFormat.format(parsedDate)
    } catch (e: Exception) {
        dateStr ?: ""
    }
}

private fun formatEventDate(dateStr: String?, timeStr: String?): String {
    return try {
        val date = dateStr ?: return ""
        val time = timeStr ?: return date
        
        // Parse date: "2024-11-15"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = dateFormat.parse(date) ?: return date
        
        // Format date: "Nov 15"
        val displayDateFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        val formattedDate = displayDateFormat.format(parsedDate)
        
        // Format time: "20:00" -> "8:00 PM"
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val parsedTime = timeFormat.parse(time)
        val displayTimeFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
        val formattedTime = parsedTime?.let { displayTimeFormat.format(it) } ?: time
        
        "$formattedDate • $formattedTime"
    } catch (e: Exception) {
        "$dateStr • $timeStr"
    }
}
