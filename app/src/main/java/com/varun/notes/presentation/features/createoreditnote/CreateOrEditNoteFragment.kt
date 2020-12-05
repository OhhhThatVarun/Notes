package com.varun.notes.presentation.features.createoreditnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.varun.notes.R
import com.varun.notes.databinding.FragmentCreateOrEditNotesBinding
import com.varun.notes.presentation.Status
import com.varun.notes.presentation.extentions.createLongToast
import com.varun.notes.presentation.extentions.getDataBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CreateOrEditNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateOrEditNotesBinding
    private val args: CreateOrEditNoteFragmentArgs by navArgs()
    private val viewModel: CreateOrEditNoteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = getDataBinding(R.layout.fragment_create_or_edit_notes, container)
        return binding.apply {
            viewModel = this@CreateOrEditNoteFragment.viewModel
            saveBtn.setOnClickListener(onSaveClick)
        }.root
    }

    private val onSaveClick = View.OnClickListener {
        if (isEditMode()) {
            viewModel.updateNote(args.id!!)
        } else {
            viewModel.createNewNote()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.note.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {

                }
                Status.ERROR -> {
                    createLongToast(R.string.error_fetching_note)
                }
            }
        })
        viewModel.createNote.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    createLongToast(R.string.success_creating)
                }
                Status.ERROR -> {
                    createLongToast(R.string.error_creating)
                }
            }
        })
        viewModel.updateNote.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    createLongToast(R.string.success_updated)
                }
                Status.ERROR -> {
                    createLongToast(R.string.error_updating)
                }
            }
        })
        if (isEditMode()) {
            viewModel.fillNoteData(args.id!!)
        }
    }

    /*
    * If an Id is passed when navigating to this destination that means
    * user wants to edit this note otherwise it means user just want to add a new note
    * */
    private fun isEditMode() = args.id != null
}