package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.di.IODispatcher
import com.hbworld.likhit.data.local.NoteEntity
import com.hbworld.likhit.data.repository.NoteRepositoryContract
import com.hbworld.likhit.domain.common.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: NoteRepositoryContract
) : FlowUseCase<Unit, List<NoteEntity>>(coroutineDispatcher) {

    override fun execute(param: Unit): Flow<List<NoteEntity>> {
        return repository.getAllNotes()
    }


}