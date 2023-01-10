package com.example.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.data.room.TodoListDao
import com.example.todolist.data.room.entities.TodoDbEntity

@Database(
  version = 1,
  entities = [
    TodoDbEntity::class
  ]
)
abstract class AppDatabase: RoomDatabase() {

  abstract fun getTodoListDao(): TodoListDao

    companion object {
    fun getDatabase(context: Context): AppDatabase {
      return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "todoList.db"
      ).build()
    }
  }

}