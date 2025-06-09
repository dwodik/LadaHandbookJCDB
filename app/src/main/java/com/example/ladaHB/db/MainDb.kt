package com.example.ladaHB.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ladaHB.ui.utils.ListItem

// класс и вообще вся папка для работы с базой данных

@Database(
    entities = [ListItem::class], // обозначаем класс, описывающий элемент БД
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val dao: Dao
}