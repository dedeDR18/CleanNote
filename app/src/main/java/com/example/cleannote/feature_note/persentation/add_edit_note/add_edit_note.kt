package com.example.cleannote.feature_note.persentation.add_edit_note

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.example.cleannote.R
import com.example.cleannote.databinding.FragmentAddEditNoteBinding

class add_edit_note : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private var title = ""
    private var description = ""

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

        binding.btnYellow.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_yellow))
        }

        binding.btnGreen.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_green))
        }

        binding.btnBlue.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_blue))
        }

        binding.btnAbu.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_abu))
        }

        binding.btnBurgandy.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_burgandy))
        }

        binding.btnRose.setOnClickListener {
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.note_rose))
        }

        title = binding.etTitle.text.toString()
        description = binding.etDescription.text.toString()

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