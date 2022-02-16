package com.example.cleannote.feature_note.persentation.add_edit_note

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
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _noteTitle = MutableStateFlow<String>("")
    val noteTitle: StateFlow<String> = _noteTitle

    private val _noteContent = MutableStateFlow<String>("")
    val noteContent: StateFlow<String> = _noteContent

    private val _noteColor = MutableStateFlow(Note.noteColors.random().toInt())
    val noteColor: StateFlow<Int> = _noteColor

    private val _eventFlow = MutableStateFlow<UiEvent>(UiEvent.empty)
    var eventFlow:StateFlow<UiEvent> = _eventFlow

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle -> {

            }

            is AddEditNoteEvent.EnteredContent -> {

            }

            is AddEditNoteEvent.ChangeColor -> {

            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        //insert with usecase


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
