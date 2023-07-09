package com.kbcoding.noteappcompose.presentation.addEditNote

import androidx.compose.ui.graphics.toArgb
import com.kbcoding.noteappcompose.featureNote.domain.model.Note
import com.kbcoding.noteappcompose.featureNote.domain.useCase.NoteUseCases
import com.kbcoding.noteappcompose.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) {

    private val _noteTitleState = MutableStateFlow(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitleState: StateFlow<NoteTextFieldState> = _noteTitleState

    private val _noteContentState = MutableStateFlow(NoteTextFieldState(
        hint = "Enter some content..."
    ))
    val noteContentState: StateFlow<NoteTextFieldState> = _noteContentState

    private val _noteColorState = MutableStateFlow(Note.noteColors.random().toArgb())
    val noteColorState: StateFlow<Int> = _noteColorState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

}