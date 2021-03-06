package com.example.cleannote.feature_note.persentation.add_edit_note

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cleannote.R
import com.example.cleannote.databinding.FragmentAddEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditNoteFragment : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!
    private var choosenColor: Int = R.color.note_burgandy

    private lateinit var navController: NavController

    private val viewModel: AddEditNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        _binding = FragmentAddEditNoteBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), viewModel.noteColor.value))
        binding.vm = viewModel
        binding.lifecycleOwner = this


        navController = Navigation.findNavController(view)

        binding.btnYellow.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_yellow))
            viewModel.changeNoteColor(R.color.note_yellow)
        }

        binding.btnGreen.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_green))
            viewModel.changeNoteColor(R.color.note_green)
        }

        binding.btnBlue.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_blue))
            viewModel.changeNoteColor(R.color.note_blue)
        }

        binding.btnAbu.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_abu))
            viewModel.changeNoteColor(R.color.note_abu)
        }

        binding.btnBurgandy.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_burgandy))
            viewModel.changeNoteColor(R.color.note_burgandy)
        }

        binding.btnRose.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_rose))
            viewModel.changeNoteColor(R.color.note_rose)
        }





        binding.fabEditnotes.apply {
            setOnClickListener {



                viewModel.onEvent(AddEditNoteEvent.EnteredTitle(binding.etTitle.text.toString()))
                viewModel.onEvent(AddEditNoteEvent.EnteredContent(binding.etDescription.text.toString()))
                //viewModel.onEvent(AddEditNoteEvent.ChangeColor(choosenColor))
                viewModel.onEvent(AddEditNoteEvent.SaveNote)

                navController.popBackStack()


                //Toast.makeText(requireActivity(), "title = ${binding.etTitle.text}, description ${binding.etDescription.text}", Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("ADDEDITNOTEFRAGMENT","note title = ${viewModel.noteTitle.value}")
        Log.d("ADDEDITNOTEFRAGMENT","note des = ${viewModel.noteContent.value}")


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}