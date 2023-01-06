package com.example.todolist.presentations.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolist.databinding.FragmentListBinding

class ListFragment : Fragment() {

  private lateinit var binding: FragmentListBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentListBinding.inflate(inflater)
    return binding.root
  }

}