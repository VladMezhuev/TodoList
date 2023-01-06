package com.example.todolist.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.data.room.entities.TodoDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {

  @Insert(entity = TodoDbEntity::class)
  fun addTodoItem(todoDbEntity: TodoDbEntity)

  @Delete(entity = TodoDbEntity::class)
  fun deleteTodoItem(todoDbEntity: TodoDbEntity)

  @Query("SELECT * FROM todoList")
  fun getTodoList(): Flow<List<TodoDbEntity>>

}
