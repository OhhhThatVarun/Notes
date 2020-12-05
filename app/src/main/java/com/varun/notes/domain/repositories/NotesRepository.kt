package com.varun.notes.domain.repositories

import com.varun.notes.domain.models.Note

interface NotesRepository {
    suspend fun addNote(note: Note)
    suspend fun editNote(note: Note)
    suspend fun deleteNote(id: String)
    suspend fun getNotes(): List<Note>
    suspend fun getNotes(id: String): Note
}