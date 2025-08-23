package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.app.IODispatcher
import com.hbworld.likhit.data.repository.NoteRepository
import com.hbworld.likhit.domain.base.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: NoteRepository
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