package com.example.ladaHB.di

import android.app.Application
import androidx.room.Room
import com.example.ladaHB.db.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// создаем этот класс для того, чтобы hilt мог инициализировать класс (который не наш - SingletonComponent::class)

@Module // чтобы hilt знал откуда брать все инстанции
@InstallIn(SingletonComponent::class) // говорит hilt о том где можно использовать этот модуль. SingletonComponent означает что можно использовать везде, есть и другие, см. документацию по hilt
object MainModule {
    @Provides // provide - предоставлять
    @Singleton // опять же если хотим чтобы каждый раз не добавлялся новый экземпляр класса помечаем его этой аннотацией
    fun provideMainDb(app: Application) :MainDb { // соответственно если возможности изменять класс у нас нет, вот таким способом переопределяем его методы, передаем контекст - наше приложение из хилт - app consumes (приложение использует) provideApplication() ()
        return Room.databaseBuilder(
            app, // вот так будет инициализироваться база данных
            MainDb::class.java,
            "ladaApp.db"
        ).createFromAsset("db/ladaApp.db").build()
        // указали при базу данных, которой нужно пользоваться сразу
        // создаем БД в DB Browser с этим же именем!
    }

}