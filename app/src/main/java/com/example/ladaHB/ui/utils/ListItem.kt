package com.example.ladaHB.ui.utils

import androidx.room.Entity
import androidx.room.PrimaryKey

// класс, обозначающий что же такое item - можно по итогу переименовать например в ItemContent
@Entity (tableName = "main_table") // для подключения к базе данных. entity - "сущность" - это же название для таблицы используем в DB browser
data class ListItem(
    @PrimaryKey(autoGenerate = true) // у каждого элемента должен быть id - вот эта аннотация его создаст
    val id: Int? = null, // собственно сам id, возможно будет null поэтому прописываем это
    val title: String,
    val imageName: String,
    val htmlName: String,
    val category: String,
    val isFavorite: Boolean
    // добавляем все эти поля с этими же именами в таблицу в базе данных в db browser, все кроме id помечаем как нотНалл и затем заполняем базу данных данными
    )