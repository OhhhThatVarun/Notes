package com.varun.notes.domain.repositories

import androidx.lifecycle.LiveData
import com.varun.notes.domain.models.Note

interface NotesRepository {
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: String)
    fun getNotes(): LiveData<List<Note>>
    suspend fun getNote(id: String): Note
}