package com.kbcoding.noteappcompose.featureNote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kbcoding.noteappcompose.ui.theme.BabyBlue
import com.kbcoding.noteappcompose.ui.theme.LightGreen
import com.kbcoding.noteappcompose.ui.theme.RedOrange
import com.kbcoding.noteappcompose.ui.theme.RedPink
import com.kbcoding.noteappcompose.ui.theme.Violet
import java.lang.Exception

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)
