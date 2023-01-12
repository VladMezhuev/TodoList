//package com.example.todolist.presentations.fragments.dialogs
//
//import androidx.lifecycle.ViewModel
//import com.example.todolist.AppDatabase
//import com.example.todolist.data.room.entities.TodoDbEntity
//import com.example.todolist.di.ApplicationScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
//@HiltViewModel
//class DeleteDialogViewModel @Inject constructor(
//  private val appDatabase: AppDatabase,
//  @ApplicationScope private val applicationScope: CoroutineScope
//): ViewModel() {
//
//  fun onConfirmDelete(item: TodoDbEntity) {
//    applicationScope.launch {
//      withContext(Dispatchers.IO) {
//        appDatabase.getTodoListDao().deleteTodoItem(item)
//      }
//    }
//  }
//
//}