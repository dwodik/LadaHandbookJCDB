package com.example.ladaHB.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ladaHB.MainViewModel
import com.example.ladaHB.ui.utils.DrawerEvents
import com.example.ladaHB.ui.utils.ListItem
import kotlinx.coroutines.launch

// класс описывает TopBar и Drawer меню
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel(), onClick: (ListItem) -> Unit/*, listId: String? = null*/) {
    val scaffoldState =
        rememberScaffoldState() // scaffoldState - "строительные леса" - можно сказать скелет
    val topBarTitle =
        remember { mutableStateOf("История") } // значение по умолчанию для топ апп бар
    // МОЖНО ЗАМЕНИТЬ БУДЕТ ЕСЛИ СТАРТОВУЮ СТРАНИЦУ ПОМЕНЯЮ
    val coroutineScope = rememberCoroutineScope() // запускаем корутины
    val mainList = mainViewModel.mainList

    val scrollPosition = rememberLazyListState() // позиция скролла запоминаем
    var categoryName = topBarTitle.value // создаем переменную без remember, чтобы эта переменная могла меняться ниже в SideEffect


    LaunchedEffect(Unit) { // "запущенный эффект" - эту функцию добавили на этапе создания избранного. без неё избранное не отображается, нужна корутина
        mainViewModel.getAllItemsByCategory(topBarTitle.value) // то, что откроется по умолчанию
    }

    SideEffect { // "побочный эффект" - моя функция для того, чтобы при открытии новой категории скролл был вверх списка
        coroutineScope.launch {
            /*if (categoryName != topBarTitle.value) { // если наименование что мы запомнили выше - не равно категории сверху в топ бар
                categoryName = topBarTitle.value // значит меняем сохраненное наименование
            }*/
            scrollPosition.scrollToItem(0) // и скроллим вверх
        }
    }


    Scaffold( // "строительные леса", то бишь всё кроме основного экрана
        scaffoldState = scaffoldState,
        topBar = {
            MainTopBar( // запускаем наш метод из класса MainTopBar
                title = topBarTitle.value, // передаем заголовок и "леса"
                scaffoldState
            ) {
                topBarTitle.value = "Избранное"
                mainViewModel.getFavorites() // открываем избранное
            }
        },
        drawerContent = { //  передаем открывающийся наш настроенный DrawerMenu
            DrawerMenu() { event -> // обрабатываем событие
                when (event) { // когда событие является онайтемклик - задаем заголовок и нужный список
                    is DrawerEvents.OnItemClick -> {
                        topBarTitle.value = event.title
                        mainViewModel.getAllItemsByCategory(event.title)
                    }
                }
                coroutineScope.launch { // запускаем корутины и только после этого можем сделать закрывание drawer после выбора какой-то позиции
                    scaffoldState.drawerState.close()
                }
            }
        }
    ) { // а здесь указываем то, что будет на основном экране (без экшнбар, без drawer, без топ бар, и т.д.)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollPosition
        ) { // создаем представление - ленивую колонку
            items(mainList.value) { item ->// колонка состоит из элементов
                MainListItem(item = item) { listItem ->  // достаем из списка нужный
                    onClick(listItem) // получаем из MainListItem и это можно получить в MainActivity
                }
            }
        }


    }
}




