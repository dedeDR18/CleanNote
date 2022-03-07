package com.example.cleannote.feature_note.domain.usecase

import android.util.Log
import com.example.cleannote.feature_note.domain.model.InvalidNoteException
import com.example.cleannote.feature_note.domain.model.Note
import com.example.cleannote.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    operator suspend fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty")
        }

        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty")
        }

        repository.insertNote(note)

    }
}