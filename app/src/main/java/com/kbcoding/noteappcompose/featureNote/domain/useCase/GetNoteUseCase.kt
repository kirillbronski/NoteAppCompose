package com.kbcoding.noteappcompose.featureNote.domain.useCase

import com.kbcoding.noteappcompose.featureNote.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.getNoteById(id)
    }
}