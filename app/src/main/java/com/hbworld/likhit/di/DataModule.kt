package com.hbworld.likhit.di

import com.hbworld.likhit.data.local.NoteDao
import com.hbworld.likhit.data.repository.NoteRepository
import com.hbworld.likhit.data.repository.NoteRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesNotesRepository(noteDao: NoteDao): NoteRepositoryContract {
        return NoteRepository(noteDao)
    }
}
