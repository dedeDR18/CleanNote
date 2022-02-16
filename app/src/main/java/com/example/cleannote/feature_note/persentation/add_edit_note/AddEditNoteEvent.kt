package com.example.cleannote.feature_note.persentation.add_edit_note

sealed class AddEditNoteEvent {
    data class EnteredTitle(val titleValue:String): AddEditNoteEvent()
    data class EnteredContent(val contentValue:String): AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}