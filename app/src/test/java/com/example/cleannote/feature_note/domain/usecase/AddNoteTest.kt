package com.example.cleannote.feature_note.domain.usecase

import com.example.cleannote.feature_note.data.repository.FakeNoteRepository
import com.example.cleannote.feature_note.domain.model.InvalidNoteException
import com.example.cleannote.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp(){

        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)
        getNotes = GetNotes(fakeRepository)

    }

    @Test
    fun `add note with title and description, success`() = runBlocking{
        val note1 = Note(
            title = "title 1",
            content = "content 1",
            timestamp = 1.toLong(),
            color = 1
        )
        addNote(note1)

        val data = getNotes().first()

        assert(data.isNotEmpty())
    }

    @Test(expected = InvalidNoteException::class)
    fun `add note with blank title, expected fail`() = runBlocking{
        val note1 = Note(
            title = "",
            content = "content 1",
            timestamp = 1.toLong(),
            color = 1
        )
        addNote(note1)

    }

    @Test(expected = InvalidNoteException::class)
    fun `add note with blank content, expected fail`() = runBlocking{
        val note1 = Note(
            title = "asd",
            content = "",
            timestamp = 1.toLong(),
            color = 1
        )
        addNote(note1)

    }
}