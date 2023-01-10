package com.example.todolist.presentations.fragments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.AppDatabase
import com.example.todolist.data.room.entities.TodoDbEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (
  private val appDatabase: AppDatabase
): ViewModel() {

  val searchQuery = MutableStateFlow<String?>("")
//  val searchQuery get() = _searchQuery.asStateFlow()

  private val _todoList = MutableStateFlow<List<TodoDbEntity>>(emptyList())
  val todoList: Flow<List<TodoDbEntity>>
    get() = _todoList.asStateFlow()

  init {
//    getList()
    searchHandle("")
  }

//  private fun getList() {
//    viewModelScope.launch {
//      withContext(Dispatchers.IO) {
//        appDatabase.getTodoListDao().getTodoList(searchQuery.value!!).collect { list ->
//          _todoList.emit(list)
//        }
//      }
//    }
//  }

  fun searchHandle(query: String?) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        searchQuery.emit(query)
        searchQuery.collect {
          appDatabase.getTodoListDao().getTodoList(it!!).collect { list ->
            _todoList.emit(list)
          }
        }
      }
    }
  }

//  fun searchHandle(query: String?) {
//    viewModelScope.launch {
//      withContext(Dispatchers.IO) {
//        if (query != null) {
//          val searchList = _todoList.value.filter { item ->
//            item.title.lowercase().contains(query.lowercase())
//          }
//          _todoList.emit(searchList)
//        }
//      }
//    }
//  }
}