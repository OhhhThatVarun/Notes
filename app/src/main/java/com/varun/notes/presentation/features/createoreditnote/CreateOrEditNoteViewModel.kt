package com.varun.notes.presentation.features.createoreditnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository
import com.varun.notes.presentation.vo.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

class CreateOrEditNoteViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    // two-way binding variables.
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()

    val note: LiveData<Resource<Note?>> get() = _note
    private val _note = MutableLiveData<Resource<Note?>>()

    val createNote: LiveData<Resource<String>> get() = _createNote
    private val _createNote = MutableLiveData<Resource<String>>()

    val updateNote: LiveData<Resource<Unit>> get() = _updateNote
    private val _updateNote = MutableLiveData<Resource<Unit>>()

    fun fillNoteData(id: String) {
        viewModelScope.launch {
            try {
                val note = notesRepository.getNote(id)
                title.value = note?.title
                description.value = note?.description
                imageUrl.value = note?.imageUrl
                _note.postValue(Resource.success(note))
            } catch (e: Exception) {
                Timber.e(e)
                _note.postValue(Resource.error(e.message))
            }
        }
    }

    fun createNewNote() {
        viewModelScope.launch {
            try {
                _createNote.postValue(Resource.success(notesRepository.addNote(getNote())))
            } catch (e: Exception) {
                Timber.e(e)
                _createNote.postValue(Resource.error(e.message))
            }
        }
    }

    fun updateNote(id: String) {
        viewModelScope.launch {
            try {
                _updateNote.postValue(Resource.success(notesRepository.updateNote(getNote().copy(id = id))))
            } catch (e: Exception) {
                Timber.e(e)
                _updateNote.postValue(Resource.error(e.message))
            }
        }
    }

    private fun getNote(): Note {
        return Note(title = title.value, description = description.value, imageUrl = imageUrl.value, createdAt = _note.value?.data?.createdAt)
    }
}