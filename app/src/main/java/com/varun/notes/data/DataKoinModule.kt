package com.varun.notes.data

import com.varun.notes.data.db.RoomClient
import com.varun.notes.data.repositories.NotesRepositoryImpl
import com.varun.notes.domain.repositories.NotesRepository
import org.koin.dsl.module

val dataKoinModule = module {

    single<NotesRepository> {
        val noteDao = RoomClient.getDatabase(get()).notesDao()
        NotesRepositoryImpl(noteDao)
    }
}