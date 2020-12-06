package com.varun.notes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.varun.notes.data.db.dao.NotesDao
import com.varun.notes.data.entities.transform
import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository
import java.util.*

class NotesRepositoryImpl(private val notesDao: NotesDao) : NotesRepository {

    override suspend fun addNote(note: Note): String {
        val newId = UUID.randomUUID().toString()
        notesDao.insert(note.copy(id = newId, createdAt = System.currentTimeMillis()).transform())
        return newId
    }

    override suspend fun updateNote(note: Note) {
        return notesDao.updateNote(note.copy(isEdited = true).transform())
    }

    override suspend fun deleteNote(id: String) {
        return notesDao.deleteNote(id)
    }

    override fun getNotes(): LiveData<List<Note>> {
        return Transformations.map(notesDao.getNotes()) { notes ->
            notes.map { it.transform() }
        }
    }

    override suspend fun getNote(id: String): Note? {
        return notesDao.getNoteById(id)?.transform()
    }
}