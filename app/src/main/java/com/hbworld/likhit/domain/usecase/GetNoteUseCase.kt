package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.di.IODispatcher
import com.hbworld.likhit.data.local.NoteEntity
import com.hbworld.likhit.data.repository.NoteRepositoryContract
import com.hbworld.likhit.domain.common.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepositoryContract
) : CoroutineUseCase<GetNoteUseCase.Param, NoteEntity>(coroutineDispatcher) {

    override suspend fun execute(params: Param): NoteEntity {
        return noteRepository.getNoteById(params.id)
    }

    data class Param(val id: Long)
}