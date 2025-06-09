package com.example.ladaHB

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ladaHB.ui.components.InfoScreen
import com.example.ladaHB.ui.components.MainScreen
import com.example.ladaHB.ui.theme.LadaTheme
import com.example.ladaHB.ui.utils.ListItem
import com.example.ladaHB.ui.utils.Routes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint // для хилт
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController() // подключаем контроллер навигации между экранами
            var item: ListItem? = null // это выбранный пункт из списка, для InfoScreen например список: классика, пункт: 2107
            // объявление темы приложения
            LadaTheme { // в NavHost запускаются экраны - передаем выше созданный navController и указываем какой экран откроется по умолчанию
                // навигацию соответственно добавляем только после того, как у нас есть с чего на что переходить
                NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
                    // поскольку у нас есть файл с константами-наименованиями, используем его. если бы не было, можно было бы внутри скобок написать название, например "main_screen"
                    // первый переход
                    composable(Routes.MAIN_SCREEN) { // сами переходы выполняются с помощью ф-ции composable
                        // Routes.MAIN_SCREEN - маршрут(пункт назначения), пункт назначения - Пункт назначения — это компонент, который будет вызываться при выполнении навигации
                        MainScreen() { listItem ->
                            item = listItem // с помощью контроллера и функции navigate и переходит сам переход
                            navController.navigate(Routes.INFO_SCREEN)
                        }


                    }
                    // второй переход
                    // Routes.INFO_SCREEN - маршрут(куда отправляемся), InfoScreen - куда идём
                    composable(Routes.INFO_SCREEN) {
                        InfoScreen(item = item!!)
                    }





                   /* composable(Routes.MAIN_SCREEN) {
                        MainScreen() { listItem ->
                            item = listItem
                            navController.navigate(Routes.INFO_SCREEN) {
                                navController.graph.startDestinationRoute?.let { homeScreen ->
                                    popUpTo(homeScreen) {
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            }
                        }

                    }*/





                }
            }
        }
    }
}

