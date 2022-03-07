package com.example.cleannote.feature_note.persentation.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleannote.R
import com.example.cleannote.databinding.FragmentAddEditNoteBinding
import com.example.cleannote.databinding.FragmentNotesBinding
import com.example.cleannote.feature_note.domain.model.Note
import com.example.cleannote.feature_note.domain.util.NoteOrder
import com.example.cleannote.feature_note.domain.util.OrderType
import com.example.cleannote.feature_note.persentation.add_edit_note.AddEditNoteViewModel
import com.example.cleannote.feature_note.persentation.notes.adapter.NotesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by activityViewModels()
    private lateinit var noteAdapter: NotesAdapter

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        initRv()
        uiEvent()
        handleDeleteButtonClick()
        handleItemClick()

        navController = Navigation.findNavController(view)
        binding.fabNotes.apply {
            setOnClickListener {
                navController.navigate(R.id.action_notesFragment_to_add_edit_note)
            }
        }
    }

    private fun initRv(){
        noteAdapter = NotesAdapter()
        binding.apply {
            rvNotes.setHasFixedSize(true)
            rvNotes.layoutManager = LinearLayoutManager(requireContext())
            val divider = DividerItemDecoration(requireContext(), (rvNotes.layoutManager as LinearLayoutManager).orientation)
            rvNotes.addItemDecoration(divider)
            rvNotes.adapter = noteAdapter
        }
    }

    private fun uiEvent(){
        lifecycleScope.launch {
            viewModel.eventUi.collect { event ->
                when(event){
                    is NotesViewModel.UiEventNotes.ShowData -> {
                        Log.d("NOTESFRAGMENT", "show data")
                        noteAdapter.setData(ArrayList(event.notes))
                    }
                    is NotesViewModel.UiEventNotes.empty -> {
                        Log.d("NOTESFRAGMENT", "empty")
                    }
                }
            }
        }
    }

    private fun handleDeleteButtonClick(){
        noteAdapter.onDeleteButtonClick = { note ->
            viewModel.onEvent(NotesEvent.DeleteNote(note))

            snackbarUndoAction()
        }
    }

    private fun snackbarUndoAction(){
        val snackbar = Snackbar.make(binding.root, "Note Deleted", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Undo") {
            viewModel.onEvent(NotesEvent.RestoreNote)
        }
        snackbar.show()
        }


    private fun handleItemClick(){
        noteAdapter.onItemClick = { note ->
            navController.navigate(R.id.action_notesFragment_to_add_edit_note, Bundle().apply { putInt("noteId", note.id!!) })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}