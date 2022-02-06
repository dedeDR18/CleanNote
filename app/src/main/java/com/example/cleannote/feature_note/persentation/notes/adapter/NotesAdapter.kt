package com.example.cleannote.feature_note.persentation.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleannote.R
import com.example.cleannote.databinding.NoteItemBinding
import com.example.cleannote.feature_note.domain.model.Note

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val listNote = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        val currentData = listNote[position]
        holder.bind(currentData)
    }

    override fun getItemCount() = listNote.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NoteItemBinding.bind(itemView)
        fun bind(data: Note) {

        }
    }
}