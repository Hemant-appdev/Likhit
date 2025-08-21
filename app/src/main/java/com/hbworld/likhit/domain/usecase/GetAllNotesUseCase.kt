package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.data.repository.NotesRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    val repository: NotesRepository
) {
}