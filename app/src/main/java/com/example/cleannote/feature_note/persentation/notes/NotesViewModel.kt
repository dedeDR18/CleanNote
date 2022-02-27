package com.example.cleannote.feature_note.persentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannote.feature_note.domain.model.Note
import com.example.cleannote.feature_note.domain.usecase.NoteUseCases
import com.example.cleannote.feature_note.domain.util.NoteOrder
import com.example.cleannote.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel(){


    // #2
    private val _state = MutableStateFlow<NotesState>(NotesState())
    val state: StateFlow<NotesState> = _state

    private val _eventUi = MutableStateFlow<UiEventNotes>(UiEventNotes.empty)
    val eventUi: StateFlow<UiEventNotes> = _eventUi


    // lastt deleted note
    private var recentlydeletedNote: Note? = null

    //
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    // #1
    fun onEvent(event: NotesEvent){
        when (event) {
            is NotesEvent.Order -> {
                if(state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }

                getNotes(event.noteOrder)

            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlydeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlydeletedNote ?: return@launch)
                    recentlydeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes,
                    noteOrder
                )
                //_eventUi.emit(UiEventNotes.ShowData())
            }
            .launchIn(viewModelScope)
    }

    sealed class UiEventNotes{
        data class ShowData(val notes: List<Note>):UiEventNotes()
        object empty: UiEventNotes()
    }
}
