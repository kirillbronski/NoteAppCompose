package com.kbcoding.noteappcompose.presentation.notes

import com.kbcoding.noteappcompose.featureNote.domain.model.Note
import com.kbcoding.noteappcompose.featureNote.domain.util.NoteOrder
import com.kbcoding.noteappcompose.featureNote.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
