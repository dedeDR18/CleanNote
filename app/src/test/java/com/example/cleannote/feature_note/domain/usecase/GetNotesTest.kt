package com.example.cleannote.feature_note.domain.usecase

import com.example.cleannote.feature_note.data.repository.FakeNoteRepository
import com.example.cleannote.feature_note.domain.model.Note
import com.example.cleannote.feature_note.domain.util.NoteOrder
import com.example.cleannote.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeRepository)

        val notesToInsert = mutableListOf<Note>()

        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )

        }

        notesToInsert.shuffle()
         runBlocking {
             notesToInsert.forEach { note ->
                 fakeRepository.insertNote(note)
             }
         }
    }

    @Test
    fun `Order notes by Title Ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        for( i in 0..notes.size - 2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by Title Descending, correct order` () = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for( i in 0..notes.size - 2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by Date Ascending, correct order` () = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for( i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by Date Descending, correct order` () = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for( i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by Color Ascending, correct order` () = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for( i in 0..notes.size - 2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `Order notes by Color Descending, correct order` () = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        for( i in 0..notes.size - 2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }

}