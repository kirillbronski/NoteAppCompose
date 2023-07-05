package com.kbcoding.noteappcompose.di

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.kbcoding.noteappcompose.App
import com.kbcoding.noteappcompose.featureNote.data.repository.NoteRepositoryImpl
import com.kbcoding.noteappcompose.featureNote.data.source.NoteDataBase
import com.kbcoding.noteappcompose.featureNote.domain.repository.NoteRepository
import com.kbcoding.noteappcompose.featureNote.domain.useCase.DeleteNoteUseCase
import com.kbcoding.noteappcompose.featureNote.domain.useCase.GetNotesUseCase
import com.kbcoding.noteappcompose.featureNote.domain.useCase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDataBase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(
            context,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(db: NoteDataBase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Singleton
    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases{
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository)
        )
    }
}