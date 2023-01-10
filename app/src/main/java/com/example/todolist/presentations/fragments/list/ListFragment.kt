package com.example.todolist.presentations.fragments.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.data.room.entities.TodoDbEntity
import com.example.todolist.databinding.FragmentListBinding
import com.example.todolist.presentations.adapters.TodoDelegate
import com.example.todolist.presentations.adapters.TodoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment : Fragment() {
  private lateinit var binding: FragmentListBinding
  private val todoListAdapter = TodoListAdapter()

  private val viewModel: ListViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentListBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initAdapter()

    todoListAdapter.attachDelegate(object : TodoDelegate {
      override fun editItem(item: TodoDbEntity) {
        TODO("Not yet implemented")
      }

      override fun toggleStatus(item: TodoDbEntity) {
        TODO("Not yet implemented")
      }

    })

    binding.apply {
      addItemButton.setOnClickListener { toAddFragment() }

      searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
//          viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//            viewModel.searchQuery.collect { query ->
//
//            }
//          }
          viewModel.searchHandle(newText)
//          viewModel.searchQuery.value = newText
//          viewModel.searchQuery.emit(newText)
          return true
        }

      })
    }

    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      viewModel.todoList.collect { list ->
        if (list.isEmpty()) {
          binding.isEmptyTv.visibility = View.VISIBLE
        } else {
          binding.isEmptyTv.visibility = View.GONE
          todoListAdapter.setList(list)
        }
      }
    }
  }

  private fun initAdapter() {
    binding.apply {
      itemsList.layoutManager = LinearLayoutManager(this@ListFragment.context)
      itemsList.adapter = todoListAdapter
    }
  }

  private fun toAddFragment() {
    val bundle = Bundle()
    bundle.putInt("MODE", 1)
    findNavController().navigate(R.id.action_listFragment_to_editItemFragment, bundle)
  }

  private fun toEditFragment() {
    findNavController().navigate(R.id.action_listFragment_to_editItemFragment)
  }

}