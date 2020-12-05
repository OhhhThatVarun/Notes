package com.varun.notes.data

import com.varun.notes.data.db.RoomClient
import com.varun.notes.data.repositories.NotesRepositoryImpl
import org.koin.dsl.module

val dataKoinModule = module {

    single {
        val noteDao = RoomClient.getDatabase(get()).notesDao()
        NotesRepositoryImpl(noteDao)
    }
}