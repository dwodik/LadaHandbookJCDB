package com.example.ladaHB

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ladaHB.db.MainDb
import com.example.ladaHB.ui.utils.ListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel позволяет отделить модель данных и логику приложения, связанную с пользовательским интерфейсом, от кода, который отвечает за отображение пользовательского интерфейса и управление им, а также взаимодействие с операционной системой. При таком подходе приложение будет состоять из одного или нескольких объектов Activity, которые представляют пользовательский интерфейс, а также ряда объектов ViewModel, которые будут отвечать за обработку данных. Классы Activity, которые представляют пользовательский интерфейс, отслеживают состояние модели во ViewModel, поэтому любые изменения данных вызывают рекомпозицию. События пользовательского интерфейса, например, нажатие кнопки, настраиваются для вызова соответствующей функции в ViewModel. По сути, это прямая реализация концепции однонаправленного потока данных

// этот класс используется в MainListItem
@HiltViewModel
class MainViewModel @Inject constructor(val mainDb: MainDb) : ViewModel() {

    val mainList = mutableStateOf(emptyList<ListItem>())
    private var job: Job? = null // с помощью job можно следить за изменениями либо в общем списке либо в списке избранного например

    fun getAllItemsByCategory(category: String) {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getAllItemsByCategory(category).collect {
                list -> mainList.value = list
            }
        }
    }

    fun insertItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.insertItem(item)
    }

    // эта функция не активна, но я подозреваю что если удалить какой-то item из БД, то изменения не отследятся, и надо будет как у меня поначалу было - чистить кэш приложения, потому что оно уже сохранило изначальное представление
    fun deleteItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.deleteItem(item)
    }

    fun getFavorites() {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getFavorites().collect {
                    list -> mainList.value = list
            }
        }
    }

}