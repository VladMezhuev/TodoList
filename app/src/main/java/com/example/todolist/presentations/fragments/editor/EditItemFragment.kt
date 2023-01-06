package com.example.todolist.presentations.fragments.editor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolist.R
import com.example.todolist.databinding.FragmentEditItemBinding

class EditItemFragment : Fragment() {
  private lateinit var binding: FragmentEditItemBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentEditItemBinding.inflate(inflater)
    return binding.root
  }

}