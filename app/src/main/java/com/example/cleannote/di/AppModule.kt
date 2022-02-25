package com.example.cleannote.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleannote.feature_note.data.data_source.NoteDao
import com.example.cleannote.feature_note.data.data_source.NoteDatabase
import com.example.cleannote.feature_note.data.repository.NoteRepositoryImpl
import com.example.cleannote.feature_note.domain.repository.NoteRepository
import com.example.cleannote.feature_note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application) : NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}