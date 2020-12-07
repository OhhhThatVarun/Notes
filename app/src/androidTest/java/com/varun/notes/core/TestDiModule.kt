package com.varun.notes.core

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.varun.notes.data.db.RoomClient
import com.varun.notes.data.repositories.NotesRepositoryImpl
import com.varun.notes.domain.repositories.NotesRepository
import com.varun.notes.presentation.features.createoreditnote.CreateOrEditNoteViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
val testDiModule = module {

    single<CoroutineDispatcher> {
        TestCoroutineDispatcher()
    }

    single<CoroutineContext> {
        TestCoroutineDispatcher()
    }

    single {
        TestCoroutineScope(get())
    }

    single<NotesRepository> {
        val roomClient = Room
                .inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, RoomClient::class.java)
                .setTransactionExecutor(get<CoroutineDispatcher>().asExecutor())
                .setQueryExecutor(get<CoroutineDispatcher>().asExecutor())
                .allowMainThreadQueries()
                .build()
        NotesRepositoryImpl(roomClient.notesDao())
    }

    viewModel {
        CreateOrEditNoteViewModel(get())
    }
}