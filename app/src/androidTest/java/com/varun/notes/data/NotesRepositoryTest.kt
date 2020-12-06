package com.varun.notes.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.varun.notes.data.db.RoomClient
import com.varun.notes.data.repositories.NotesRepositoryImpl
import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NotesRepositoryTest {

    private lateinit var roomClient: RoomClient
    private lateinit var notesRepository: NotesRepository
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        initDb()
        notesRepository = NotesRepositoryImpl(roomClient.notesDao())
    }

    private fun initDb() {
        roomClient = Room
            .inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                RoomClient::class.java
            )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
    }

    @After
    fun closeDb() {
        roomClient.close()
    }

    @Test
    fun insertNoteSavesData() = testScope.runBlockingTest {
        val insertedNote = insertTestNote()
        val savedNote = notesRepository.getNote(insertedNote.id)
        assert(savedNote?.id == insertedNote.id)
    }

    @Test
    fun editingNoteEditsDataAndMarksItEdited() = testScope.runBlockingTest {
        val insertedNote = insertTestNote()

        val editedTitle = "Edited title"
        notesRepository.updateNote(insertedNote.copy(title = editedTitle))

        val editedNote = notesRepository.getNote(insertedNote.id)
        assert(editedNote?.title == editedTitle)
        assert(editedNote?.isEdited == true)
    }

    @Test
    fun deleteNoteDeletesData() = testScope.runBlockingTest {
        val insertedNote = insertTestNote()
        notesRepository.deleteNote(insertedNote.id)
        val deletedNote = notesRepository.getNote(insertedNote.id)
        assert(deletedNote == null)

    }

    private suspend fun insertTestNote(): Note {
        val newNote =
            Note(title = "Test title", description = "Test description", imageUrl = "Test imageUrl")
        return newNote.copy(id = notesRepository.addNote(newNote))
    }
}