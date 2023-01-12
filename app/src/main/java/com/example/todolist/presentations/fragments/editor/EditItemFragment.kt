package com.example.todolist.presentations.fragments.editor

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.data.entites.AddItem
import com.example.todolist.data.room.entities.TodoDbEntity
import com.example.todolist.databinding.FragmentEditItemBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditItemFragment : Fragment() {
  private lateinit var binding: FragmentEditItemBinding

  private val viewModel: EditItemViewModel by viewModels()
  private var fragmentMode by Delegates.notNull<Int>()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentEditItemBinding.inflate(inflater)
    return binding.root
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    fragmentMode = requireArguments().getInt("MODE")

    when(fragmentMode) {
      1 -> { launchCreateMode() }
      2 -> { launchEditMode() }
    }

    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      viewModel.addEditTaskEvent.collect { event ->
        when (event) {
          is EditItemViewModel.AddEditTaskEvent.ShowInvalidInputMessage -> {
            Toast.makeText(this@EditItemFragment.context, event.message, Toast.LENGTH_LONG).show()
          }
          is EditItemViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
            setFragmentResult(
              "add_edit_request",
              bundleOf("add_edit_result" to event.result)
            )
            findNavController().popBackStack()
          }
        }
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun launchCreateMode() {

    binding.apply {

      deleteButton.visibility = View.GONE

      createButton.setOnClickListener {
        val title = itemTitleEt.text.toString()
        val text = itemTextEt.text.toString()
        val createdAt = getCurrentDate()
        val priority = priorityRadioGroup.checkedRadioButtonId

        viewModel.addTodoItem(title, text, createdAt, priority)
      }
    }
  }

  private fun launchEditMode() {
    val item = arguments?.getParcelable<TodoDbEntity>("item")
    binding.apply {
      itemTitleEt.setText(item!!.title)
      itemTextEt.setText(item.text)
      priorityRadioGroup.check(item.priority)

      deleteButton.visibility = View.VISIBLE
      deleteButton.setOnClickListener {
        viewModel.deleteTodoItem(item)
        requireActivity().onBackPressed()
      }

      createButton.setOnClickListener {
        val title = itemTitleEt.text.toString()
        val text = itemTextEt.text.toString()
        val priority = priorityRadioGroup.checkedRadioButtonId
        viewModel.editTodoItem(item, title, text, priority)
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun getCurrentDate(): String {
    val date = LocalDateTime.now()
      .format(DateTimeFormatter.ofPattern("dd, hh:mm"))
    return date.toString()
  }

}