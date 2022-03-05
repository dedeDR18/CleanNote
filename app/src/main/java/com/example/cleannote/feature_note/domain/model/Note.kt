package com.example.cleannote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleannote.R
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
        val noteColors = listOf(R.color.note_yellow, R.color.note_green, R.color.note_blue, R.color.note_abu, R.color.note_rose, R.color.note_burgandy)
    }
}


class InvalidNoteException(message: String): Exception(message)
