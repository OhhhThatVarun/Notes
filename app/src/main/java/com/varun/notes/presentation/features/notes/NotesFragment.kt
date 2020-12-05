package com.varun.notes.presentation.features.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.varun.notes.R
import com.varun.notes.databinding.FragmentNotesBinding
import com.varun.notes.presentation.extentions.getDataBinding
import com.varun.notes.presentation.extentions.navigate

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = getDataBinding(R.layout.fragment_notes, container)
        return binding.apply {
            addNoteBtn.setOnClickListener(onAddNoteClick)
        }.root
    }

    private val onAddNoteClick = View.OnClickListener {
        navigate(NotesFragmentDirections.actionNotesFragmentToCreateEditNotesFragment())
    }
}