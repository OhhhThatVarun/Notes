package com.varun.notes.presentation.features.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.varun.notes.R
import com.varun.notes.databinding.FragmentNotesBinding
import com.varun.notes.presentation.extentions.createLongToast
import com.varun.notes.presentation.extentions.getDataBinding
import com.varun.notes.presentation.extentions.navigate
import com.varun.notes.presentation.extentions.showAlert
import com.varun.notes.presentation.features.adapters.NotesRecyclerViewAdapter
import com.varun.notes.presentation.features.vo.Status
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private val viewModel: NotesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = getDataBinding(R.layout.fragment_notes, container)
        return binding.apply {
            adapter = NotesRecyclerViewAdapter(::onNoteClick, ::onNoteLongClick)
            addNoteBtn.setOnClickListener(onAddNoteClick)
        }.root
    }

    private val onAddNoteClick = View.OnClickListener {
        navigate(NotesFragmentDirections.actionNotesFragmentToCreateEditNotesFragment())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            binding.adapter?.setNotes(notes ?: listOf())
        }
        viewModel.deleteNote.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    createLongToast(R.string.success_deleted)
                }
                Status.ERROR -> {
                    createLongToast(R.string.error_deleting)
                }
            }
        })
    }

    private fun onNoteClick(id: String) {
        Timber.d(id)
        navigate(NotesFragmentDirections.actionNotesFragmentToCreateEditNotesFragment(id))
    }

    private fun onNoteLongClick(id: String) {
        Timber.d(id)
        showAlert(R.string.delete, R.string.delete_this_note) {
            viewModel.deleteNote(id)
        }
    }
}