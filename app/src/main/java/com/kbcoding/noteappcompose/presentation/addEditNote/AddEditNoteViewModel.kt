package com.kbcoding.noteappcompose.presentation.addEditNote

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kbcoding.noteappcompose.featureNote.domain.model.InvalidNoteException
import com.kbcoding.noteappcompose.featureNote.domain.model.Note
import com.kbcoding.noteappcompose.featureNote.domain.useCase.NoteUseCases
import com.kbcoding.noteappcompose.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentNoteId: Int? = null

    private val _noteTitleState = MutableStateFlow(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )
    val noteTitleState: StateFlow<NoteTextFieldState> = _noteTitleState

    private val _noteContentState = MutableStateFlow(
        NoteTextFieldState(
            hint = "Enter some content..."
        )
    )
    val noteContentState: StateFlow<NoteTextFieldState> = _noteContentState

    private val _noteColorState = MutableStateFlow(Note.noteColors.random().toArgb())
    val noteColorState: StateFlow<Int> = _noteColorState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitleState.value = noteTitleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContentState.value = noteContentState.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColorState.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitleState.value = noteTitleState.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitleState.value = noteTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitleState.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContentState.value = noteContentState.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContentState.value = noteContentState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContentState.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColorState.value = event.color
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(
                            Note(
                                id = currentNoteId,
                                title = noteTitleState.value.text,
                                content = noteContentState.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColorState.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

}




















