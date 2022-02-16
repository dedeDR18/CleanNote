package com.example.cleannote.feature_note.persentation.add_edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannote.feature_note.domain.model.InvalidNoteException
import com.example.cleannote.feature_note.domain.model.Note
import com.example.cleannote.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = MutableStateFlow<String>("")
    val noteTitle: StateFlow<String> = _noteTitle

    private val _noteContent = MutableStateFlow<String>("")
    val noteContent: StateFlow<String> = _noteContent

    private val _noteColor = MutableStateFlow(Note.noteColors.random().toInt())
    val noteColor: StateFlow<Int> = _noteColor

    private val _eventFlow = MutableStateFlow<UiEvent>(UiEvent.empty)
    var eventFlow:StateFlow<UiEvent> = _eventFlow

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                        _noteColor.value = note.color
                    }
                }
            }
        }

    }

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = event.titleValue
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteTitle.value = event.contentValue
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value,
                                content = noteContent.value,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )


                        _eventFlow.emit(UiEvent.saveNote)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "couldnt save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object saveNote: UiEvent()
        object empty: UiEvent()
    }
}
