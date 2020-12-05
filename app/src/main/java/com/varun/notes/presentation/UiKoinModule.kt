package com.varun.notes.presentation

import com.varun.notes.presentation.features.createoreditnote.CreateOrEditNoteViewModel
import com.varun.notes.presentation.features.notes.NotesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiKoinModule = module {

    viewModel {
        CreateOrEditNoteViewModel(get())
    }

    viewModel {
        NotesViewModel(get())
    }
}