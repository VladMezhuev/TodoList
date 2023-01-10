package com.example.todolist.presentations.fragments.editor

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.todolist.data.entites.AddItem
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
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun launchCreateMode() {

    binding.apply {

      deleteButton.visibility = View.GONE

      createButton.setOnClickListener {
        val data = AddItem(
          title = itemTitleEt.text.toString(),
          text = itemTextEt.text.toString(),
          createdAt = getCurrentDate(),
          priority = priorityRadioGroup.checkedRadioButtonId,
        )
        viewModel.addTodoItem(data)
        requireActivity().onBackPressed()
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