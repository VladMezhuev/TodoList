package com.example.todolist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.data.room.entities.TodoDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {

  @Insert(entity = TodoDbEntity::class)
  fun addTodoItem(todoDbEntity: TodoDbEntity)

//  @Delete(entity = TodoDbEntity::class)
//  fun deleteTodoItem(todoDbEntity: TodoDbEntity)

  @Query("SELECT * FROM todoList WHERE title LIKE '%' || :searchQuery || '%' ")
  fun getTodoList(searchQuery: String): Flow<List<TodoDbEntity>>

//  @Query("SELECT * FROM todoList")
//  fun getTodoList(): Flow<List<TodoDbEntity>>

//  @Query("SELECT * FROM todoList WHERE id = :id")
//  fun getTodoItemById(id: Int): Flow<TodoDbEntity>

}
