//package com.example.todolist.presentations.fragments.dialogs
//
//import android.app.AlertDialog
//import android.app.Dialog
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.viewModels
//
//class DeleteDialogFragment: DialogFragment(){
//
//  private val viewModel: DeleteDialogViewModel by viewModels()
//
//  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//    return AlertDialog.Builder(requireContext())
//      .setTitle("Удаление")
//      .setMessage("Удалить элемент?")
//      .setNegativeButton("Нет", null)
//      .setPositiveButton("Да") { _, _ ->
////        viewModel.onConfirmDelete()
//      }
//      .create()
//  }
//
//}
