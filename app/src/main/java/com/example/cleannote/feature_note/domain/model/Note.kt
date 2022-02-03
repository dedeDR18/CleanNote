package com.example.cleannote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleannote.ui.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(Red, Cyan, Magenta, Green, Yellow)
    }
}
