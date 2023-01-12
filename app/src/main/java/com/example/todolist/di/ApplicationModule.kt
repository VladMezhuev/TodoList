//package com.example.todolist.di
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.SupervisorJob
//import javax.inject.Qualifier
//import javax.inject.Singleton
//
//@Module
//object ApplicationModule {
//
//  @ApplicationScope
//  @Provides
//  @Singleton
//  fun provideApplicationScope() = CoroutineScope(SupervisorJob())
//
//}
//
//@Retention(AnnotationRetention.RUNTIME)
//@Qualifier
//annotation class ApplicationScope