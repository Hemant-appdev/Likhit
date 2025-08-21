package com.hbworld.likhit.app

import com.hbworld.likhit.data.local.NoteDao
import com.hbworld.likhit.data.repository.NotesRepository
import com.hbworld.likhit.data.repository.NotesRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesNotesRepository(noteDao: NoteDao): NotesRepositoryContract {
        return NotesRepository(noteDao)
    }
}
