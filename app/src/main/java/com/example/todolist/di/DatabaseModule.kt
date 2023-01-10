package com.example.todolist.di

import android.content.Context
import com.example.todolist.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
    return AppDatabase.getDatabase(context = context)
  }

}