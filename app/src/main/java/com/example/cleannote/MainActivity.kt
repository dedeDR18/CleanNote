package com.example.cleannote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.cleannote.databinding.ActivityMainBinding
import com.example.cleannote.databinding.OrderDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding


    private lateinit var navController: NavController
    var listSelected = mutableListOf("title", "ascending")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = findNavController(R.id.main_fragment)

        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_notesFragment_to_add_edit_note)
        }


    }




    private fun showOrderDialog(){
        val bottomSheet = BottomSheetDialog(this)
        val bindingSheet = DataBindingUtil.inflate<OrderDialogBinding>(
            layoutInflater,R.layout.order_dialog, null, false
        )
        bottomSheet.setContentView(bindingSheet.root)


        bindingSheet.btnFilter.setOnClickListener {

            val idNote = bindingSheet.rgNote.checkedRadioButtonId
            var idOrder = bindingSheet.rgOrder.checkedRadioButtonId


            if (idNote == -1 ) {
                Toast.makeText(this, "Nothing selected !", Toast.LENGTH_SHORT).show()
            } else {
                val radioSelected = bindingSheet.rgNote.findViewById<RadioButton>(idNote)
                listSelected.set(0,radioSelected.text.toString())
            }

            if (idOrder == -1 ) {
                Toast.makeText(this, "Nothing selected !", Toast.LENGTH_SHORT).show()
            } else {
                val radioSelected = bindingSheet.rgOrder.findViewById<RadioButton>(idOrder)
                listSelected.set(1,radioSelected.text.toString())
            }

            Toast.makeText(this, "Selected = ${listSelected.joinToString()}", Toast.LENGTH_SHORT).show()
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_order, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_order){
            showOrderDialog()
        }
        return super.onOptionsItemSelected(item)
    }


}