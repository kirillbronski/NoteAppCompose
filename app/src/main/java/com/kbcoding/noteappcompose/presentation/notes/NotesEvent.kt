package com.kbcoding.noteappcompose.presentation.notes

import com.kbcoding.noteappcompose.featureNote.domain.model.Note
import com.kbcoding.noteappcompose.featureNote.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
