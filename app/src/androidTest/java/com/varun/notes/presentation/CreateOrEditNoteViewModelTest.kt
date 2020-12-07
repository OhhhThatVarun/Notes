package com.varun.notes.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.varun.notes.domain.models.Note
import com.varun.notes.domain.repositories.NotesRepository
import com.varun.notes.presentation.features.createoreditnote.CreateOrEditNoteViewModel
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
class CreateOrEditNoteViewModelTest : KoinTest {

    private lateinit var createOrEditNoteViewModel: CreateOrEditNoteViewModel
    private val notesRepository: NotesRepository by inject()
    private val testScope: TestCoroutineScope by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        createOrEditNoteViewModel = CreateOrEditNoteViewModel(notesRepository)
    }

    @Test
    fun fillNoteDataUpdatesTwoWayBingingVariables() {
        testScope.runBlockingTest {
            val note = insertTestNote(notesRepository)
            createOrEditNoteViewModel.fillNoteData(note.id)
            createOrEditNoteViewModel.title.observeForever {
                assert(it == note.title)
            }
            createOrEditNoteViewModel.description.observeForever {
                assert(it == note.description)
            }
            createOrEditNoteViewModel.imageUrl.observeForever {
                assert(it == note.imageUrl)
            }
        }
    }

    @Test
    fun createNewNoteUpdatesLiveData() {
        testScope.runBlockingTest {
            val note = Note(title = "Test title", description = "Test description", imageUrl = "Test imageUrl")
            createOrEditNoteViewModel.title.postValue(note.title)
            createOrEditNoteViewModel.description.postValue(note.description)
            createOrEditNoteViewModel.imageUrl.postValue(note.imageUrl)

            createOrEditNoteViewModel.createNewNote()
            createOrEditNoteViewModel.note.observeForever { resource ->
                assert(resource.data?.title == note.title)
                assert(resource.data?.description == note.description)
                assert(resource.data?.imageUrl == note.imageUrl)
            }
        }
    }

    @Test
    fun updateNoteUpdatesLiveData() {
        testScope.runBlockingTest {
            val note = insertTestNote(notesRepository)
            createOrEditNoteViewModel.fillNoteData(note.id)
            createOrEditNoteViewModel.title.postValue("Updated title")

            createOrEditNoteViewModel.updateNote(note.id)
            createOrEditNoteViewModel.updateNote.observeForever { resource ->
                assert(resource.status == Status.SUCCESS)
            }
        }
    }
}