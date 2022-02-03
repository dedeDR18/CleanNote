package com.example.cleannote.feature_note.domain.usecase

import com.example.cleannote.feature_note.domain.model.Note
import com.example.cleannote.feature_note.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {

    operator suspend fun invoke(note: Note){
        repository.deleteNote(note)
    }
}