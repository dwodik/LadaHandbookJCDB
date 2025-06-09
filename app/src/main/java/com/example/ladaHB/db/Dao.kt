package com.example.ladaHB.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ladaHB.ui.utils.ListItem

// Dao - data access object - объект доступа к данным базы данных
@Dao
interface Dao { // надо изучить видео с базами данных
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // suspend - приостановить - функция работает через корутину
    suspend fun insertItem(item: ListItem)
    @Delete
    suspend fun deleteItem(item: ListItem) //

    @Query("SELECT * FROM main_table WHERE category LIKE :category") // :category указывает на category ниже, что получается через getAllItemsByCategory
    fun getAllItemsByCategory(category: String): kotlinx.coroutines.flow.Flow<List<ListItem>> // убрал suspend и добавил Flow поток - для того, чтобы при изменении данных обновлялся список

    @Query("SELECT * FROM main_table WHERE isFavorite = 1") // вывод избранных
    fun getFavorites(): kotlinx.coroutines.flow.Flow<List<ListItem>>
}