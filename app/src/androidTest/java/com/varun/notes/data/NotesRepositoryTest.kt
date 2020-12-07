package com.varun.notes.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.varun.notes.domain.repositories.NotesRepository
import com.varun.notes.utils.insertTestNote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NotesRepositoryTest : KoinTest {

    private val notesRepository: NotesRepository by inject()
    private val testScope: TestCoroutineScope by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertNoteSavesData() = testScope.runBlockingTest {
        val insertedNote = insertTestNote(notesRepository)
        val savedNote = notesRepository.getNote(insertedNote.id)
        assert(savedNote?.id == insertedNote.id)
        assert(savedNote?.createdAt != null)
    }

    @Test
    fun editingNoteEditsDataAndMarksItEdited() = testScope.runBlockingTest {
        val insertedNote = insertTestNote(notesRepository)

        val editedTitle = "Edited title"
        notesRepository.updateNote(insertedNote.copy(title = editedTitle))

        val editedNote = notesRepository.getNote(insertedNote.id)
        assert(editedNote?.title == editedTitle)
        assert(editedNote?.isEdited == true)
    }

    @Test
    fun deleteNoteDeletesData() = testScope.runBlockingTest {
        val insertedNote = insertTestNote(notesRepository)
        notesRepository.deleteNote(insertedNote.id)
        val deletedNote = notesRepository.getNote(insertedNote.id)
        assert(deletedNote == null)

    }
}