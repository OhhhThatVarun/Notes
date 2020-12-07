package com.varun.notes.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.varun.notes.domain.repositories.NotesRepository
import com.varun.notes.presentation.features.notes.NotesViewModel
import com.varun.notes.presentation.vo.Status
import com.varun.notes.utils.insertTestNote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NotesViewModelTest : KoinTest {

    private lateinit var notesViewModel: NotesViewModel
    private val notesRepository: NotesRepository by inject()
    private val testScope: TestCoroutineScope by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        notesViewModel = NotesViewModel(notesRepository)
    }

    @Test
    fun fetchesNotesFromRepository() = testScope.runBlockingTest {
        for (i in 0..5) {
            insertTestNote(notesRepository)
        }

        notesViewModel.notes.observeForever {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun deleteNoteUpdatesLiveData() = testScope.runBlockingTest {
        val noteToDelete = insertTestNote(notesRepository)
        notesViewModel.deleteNote(noteToDelete.id)
        notesViewModel.deleteNote.observeForever { resource ->
            assert(resource.status == Status.SUCCESS)
        }
    }
}