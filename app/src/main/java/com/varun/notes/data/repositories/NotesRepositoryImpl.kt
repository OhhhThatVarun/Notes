package com.varun.notes.data.repositories

import com.varun.notes.data.db.dao.NotesDao
import com.varun.notes.data.entities.transform
import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository
import java.util.*

class NotesRepositoryImpl(private val notesDao: NotesDao) : NotesRepository {

    override suspend fun addNote(note: Note) {
        return notesDao.insert(note.copy(id = UUID.randomUUID().toString(), createdAt = System.currentTimeMillis()).transform())
    }

    override suspend fun updateNote(note: Note) {
        return notesDao.updateNote(note.copy(isEdited = true).transform())
    }

    override suspend fun deleteNote(id: String) {
        return notesDao.deleteNote(id)
    }

    override suspend fun getNotes(): List<Note> {
        return notesDao.getNotes().map { it.transform() }
    }

    override suspend fun getNote(id: String): Note {
        return notesDao.getNoteById(id).transform()
    }
}