package com.example.cleannote.feature_note.persentation.notes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cleannote.R
import com.example.cleannote.databinding.NoteItemBinding
import com.example.cleannote.feature_note.domain.model.Note

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val listNote = ArrayList<Note>()
    var onDeleteButtonClick: ((Note) -> Unit)? = null
    var onItemClick: ((Note) -> Unit)? = null


    fun setData(data: ArrayList<Note>){
        if (data.isNotEmpty()) {
            listNote.clear()
            listNote.addAll(data)
            notifyDataSetChanged()
        }
    }

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

            binding.tvTitle.text = data.title
            binding.tvDescription.text = data.content
            binding.root.setOnClickListener {
                onItemClick?.invoke(data)
            }

            binding.itemContainer.setBackgroundColor(ContextCompat.getColor(binding.root.context, data.color))

            binding.btnDelete.setOnClickListener {
                onDeleteButtonClick?.invoke(data)
            }

        }
    }
}