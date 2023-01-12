package com.example.todolist.presentations.fragments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.AppDatabase
import com.example.todolist.data.room.entities.TodoDbEntity
import com.example.todolist.presentations.ADD_TODO_RESULT_OK
import com.example.todolist.presentations.EDIT_TODO_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (
  private val appDatabase: AppDatabase
): ViewModel() {

  var searchQuery = MutableStateFlow<String?>("")

  val sortOrder = MutableStateFlow(SortOrder.BY_PRIORITY)

  private val _todoList = MutableStateFlow<List<TodoDbEntity>>(emptyList())
  val todoList: Flow<List<TodoDbEntity>>
    get() = _todoList.asStateFlow()

  private val todoEventChannel = Channel<TodoEvent>()
  val todoEvent = todoEventChannel.receiveAsFlow()

  init {
    setList()
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  private fun setList() {
    val todoFlow = combine(
      searchQuery,
      sortOrder
    ) { query, sortOrder ->
      Pair(query, sortOrder)
    }.flatMapLatest { (query, sortOrder) ->
      appDatabase.getTodoListDao().getTodoList(query!!, sortOrder)
    }

    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        todoFlow.collect { list ->
          _todoList.emit(list)
        }
      }
    }
  }

  fun toggleStatus(todoDbEntity: TodoDbEntity) {
    viewModelScope.launch {
      appDatabase.getTodoListDao().updateItem(todoDbEntity.copy(status = !todoDbEntity.status))
    }
  }

  fun onAddEditResult(result: Int) {
    when(result) {
      ADD_TODO_RESULT_OK -> showTodoSavedMessage("Task added")
      EDIT_TODO_RESULT_OK -> showTodoSavedMessage("Task updated")
    }
  }

  private fun showTodoSavedMessage(text: String) {
    viewModelScope.launch {
      todoEventChannel.send(TodoEvent.ShowTodoSavedMessage(text))
    }
  }

  sealed class TodoEvent {
    data class ShowTodoSavedMessage(val message: String): TodoEvent()
  }

}

enum class SortOrder { BY_DATE, BY_PRIORITY }