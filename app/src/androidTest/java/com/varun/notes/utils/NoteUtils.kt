package com.varun.notes.utils

import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository

suspend fun insertTestNote(notesRepository: NotesRepository): Note {
    val newNote = Note(title = "Test title", description = "Test description", imageUrl = "Test imageUrl")
    return newNote.copy(id = notesRepository.addNote(newNote))
}