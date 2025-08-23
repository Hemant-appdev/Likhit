package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.app.IODispatcher
import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.data.repository.NoteRepository
import com.hbworld.likhit.domain.base.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) : CoroutineUseCase<GetNoteUseCase.Param, Note>(coroutineDispatcher) {

    override suspend fun execute(params: Param): Note {
        return noteRepository.getNoteById(params.id)
    }

    data class Param(val id: Long)
}