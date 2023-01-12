package com.example.todolist.data.room

import androidx.room.*
import com.example.todolist.data.room.entities.TodoDbEntity
import com.example.todolist.presentations.fragments.list.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {

  fun getTodoList(query: String, sortOrder: SortOrder): Flow<List<TodoDbEntity>> {
    return when(sortOrder) {
      SortOrder.BY_DATE -> getTodoListSortedByDate(query)
      SortOrder.BY_PRIORITY -> getTodoListSortedByPriority(query)
    }
  }

  @Query("SELECT * FROM todoList WHERE title LIKE '%' || :searchQuery || '%' ORDER BY created_at DESC")
  fun getTodoListSortedByDate(searchQuery: String): Flow<List<TodoDbEntity>>

  @Query("SELECT * FROM todoList WHERE title LIKE '%' || :searchQuery || '%' ORDER BY priority DESC")
  fun getTodoListSortedByPriority(searchQuery: String): Flow<List<TodoDbEntity>>

  @Insert(entity = TodoDbEntity::class)
  fun addTodoItem(todoDbEntity: TodoDbEntity)

  @Update
  suspend fun updateItem(todoDbEntity: TodoDbEntity)

  @Delete(entity = TodoDbEntity::class)
  fun deleteTodoItem(todoDbEntity: TodoDbEntity)

//  @Query("SELECT * FROM todoList")
//  fun getTodoList(): Flow<List<TodoDbEntity>>

//  @Query("SELECT * FROM todoList WHERE id = :id")
//  fun getTodoItemById(id: Int): Flow<TodoDbEntity>

}
