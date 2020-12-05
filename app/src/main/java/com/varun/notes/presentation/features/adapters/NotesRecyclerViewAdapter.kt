package com.varun.notes.presentation.features.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.varun.notes.R
import com.varun.notes.databinding.ItemNoteBinding
import com.varun.notes.domain.models.Note

class NotesRecyclerViewAdapter(private val onNoteClick: (String) -> Unit, private val onNoteLongClick: (String) -> Unit) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    private val notes = mutableListOf<Note>()

    fun setNotes(notes: List<Note>) {
        this.notes.apply {
            clear()
            addAll(notes)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_note, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position], onNoteClick, onNoteLongClick)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note, onNoteClick: (String) -> Unit, onNoteLongClick: (String) -> Unit) {
            binding.apply {
                this.note = note
                root.setOnClickListener {
                    onNoteClick(note.id)
                }
                root.setOnLongClickListener {
                    onNoteLongClick(note.id)
                    true
                }
                executePendingBindings()
            }
        }
    }
}