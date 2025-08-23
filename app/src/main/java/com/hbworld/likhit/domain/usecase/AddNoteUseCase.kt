package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.di.IODispatcher
import com.hbworld.likhit.data.repository.NoteRepositoryContract
import com.hbworld.likhit.domain.common.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: NoteRepositoryContract
) : CoroutineUseCase<AddNoteUseCase.Param, Long>(coroutineDispatcher) {

    override suspend fun execute(params: Param): Long {
        return repository.addNote(
            title = params.title,
            description = params.description
        )
    }

    data class Param(
        val title: String,
        val description: String
    )
}