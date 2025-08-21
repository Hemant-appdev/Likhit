package com.hbworld.likhit.domain.usecase

import com.hbworld.likhit.data.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    val repository: NotesRepository
) {


}