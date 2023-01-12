package com.example.todolist.presentations.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
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

    setHasOptionsMenu(true)

    initAdapter()

//  RecycleView events

    todoListAdapter.attachDelegate(object : TodoDelegate {
      override fun editItem(item: TodoDbEntity) {
        toEditFragment(item)
      }

      override fun toggleStatus(item: TodoDbEntity) {
        viewModel.toggleStatus(item)
      }

    })

//  Search

    binding.apply {
      addItemButton.setOnClickListener { toAddFragment() }

      searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          viewModel.searchQuery.value = newText
          return true
        }

      })
    }

// Fragment result

    setFragmentResultListener("add_edit_request") { _, bundle ->
      val result = bundle.getInt("add_edit_result")
      viewModel.onAddEditResult(result)
    }

//  RecycleView conditions

    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      viewModel.todoList.collect { list ->
        if (list.isEmpty()) {
          binding.isEmptyTv.visibility = View.VISIBLE
          setAdapterContent(list)
        } else {
          binding.isEmptyTv.visibility = View.GONE
          setAdapterContent(list)
        }
      }
    }

//  Events

    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      viewModel.todoEvent.collect { event ->
        when(event) {
          is ListViewModel.TodoEvent.ShowTodoSavedMessage -> {
            Toast.makeText(this@ListFragment.context, event.message, Toast.LENGTH_LONG).show()
          }
        }

      }
    }

  }

//  Menu

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_fragment, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId) {
      R.id.action_sort_by_date -> {
        viewModel.sortOrder.value = SortOrder.BY_DATE
        Log.d("MyLog", viewModel.sortOrder.value.toString())
        true
      }
      R.id.action_sort_by_priority -> {
        viewModel.sortOrder.value = SortOrder.BY_PRIORITY
        Log.d("MyLog", viewModel.sortOrder.value.toString())
        true
      }
      else -> false
    }
  }

//  Set adapter`s content

  private fun setAdapterContent(list: List<TodoDbEntity>) {
    todoListAdapter.setList(list)
  }

// Adapter

  private fun initAdapter() {
    binding.apply {
      itemsList.layoutManager = LinearLayoutManager(this@ListFragment.context)
      itemsList.adapter = todoListAdapter
    }
  }

//  Navigation

  private fun toAddFragment() {
    val bundle = Bundle()
    bundle.putInt("MODE", 1)
    findNavController().navigate(R.id.action_listFragment_to_editItemFragment, bundle)
  }

  private fun toEditFragment(todoItem: TodoDbEntity) {
    val bundle = Bundle()
    bundle.putInt("MODE", 2)
    bundle.putParcelable("item", todoItem)
    findNavController().navigate(R.id.action_listFragment_to_editItemFragment, bundle)
  }

}