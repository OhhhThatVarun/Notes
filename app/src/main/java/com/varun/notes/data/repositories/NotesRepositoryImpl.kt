package com.varun.notes.data.repositories

import com.varun.notes.data.db.dao.NotesDao
import com.varun.notes.data.entities.transform
import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository

class NotesRepositoryImpl(private val notesDao: NotesDao) : NotesRepository {

    override suspend fun addNote(note: Note) {
        return notesDao.insert(note.transform())
    }

    override suspend fun editNote(note: Note) {
        return notesDao.updateNote(note.transform())
    }

    override suspend fun deleteNote(id: String) {
        return notesDao.deleteNote(id)
    }

    override suspend fun getNotes(): List<Note> {
        return notesDao.getNotes().map { it.transform() }
    }

    override suspend fun getNotes(id: String): Note {
        return notesDao.getNoteById(id).transform()
    }
}