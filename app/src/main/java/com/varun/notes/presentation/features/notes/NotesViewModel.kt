package com.varun.notes.presentation.features.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository
import com.varun.notes.presentation.vo.Resource
import com.varun.notes.presentation.vo.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    val notes: LiveData<List<Note>> = notesRepository.getNotes()

    val deleteNote: LiveData<Resource<Unit>> get() = _deleteNote
    private val _deleteNote = SingleLiveEvent<Resource<Unit>>()

    fun deleteNote(id: String) {
        viewModelScope.launch {
            try {
                _deleteNote.postValue(Resource.success(notesRepository.deleteNote(id)))
            } catch (e: Exception) {
                Timber.e(e)
                _deleteNote.postValue(Resource.error(e.message))
            }
        }
    }
}