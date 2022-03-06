package com.example.cleannote.feature_note.persentation.util

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object BindingUtils {

    @JvmStatic
    @BindingAdapter("android:setBackground")
    fun setBackground(viewGroup: ViewGroup, color: Int){
        //binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), viewModel.noteColor.value))
        viewGroup.setBackgroundColor(ContextCompat.getColor(viewGroup.context, color))
    }
}