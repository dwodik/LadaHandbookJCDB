package com.example.ladaHB.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.ladaHB.R
import com.example.ladaHB.ui.theme.ColorMain
import kotlinx.coroutines.launch

// класс, описывающий TopBar - используется в MainScreen
@Composable
fun MainTopBar(
    title: String, // получаем название которое нужно вывести
    scaffoldState: ScaffoldState, // получаем строительные леса
    onFavoriteClick: () -> Unit
) {
    val coroutine = rememberCoroutineScope() // это функция, которая создаёт область действия корутин, привязанную к жизненному циклу составной функции
    //Она позволяет запускать корутины за пределами составной функции, но так, чтобы они автоматически отменялись после выхода из композиции. Также rememberCoroutineScope используется, когда нужно вручную управлять жизненным циклом одной или нескольких корутин, например, отменить анимацию при появлении пользовательского события.
    val fontLada = FontFamily(Font(R.font.lada_pragmatica)) // подготовим шрифт для топБар

    TopAppBar( // верхняя панель приложения
        //  // передаем заголовок и шрифт
        title = {
            Text(
                text = title,
                fontFamily = fontLada
            )
        },
        backgroundColor = ColorMain, // ставим цвет схожий с основным цветом на изображении drawer
        contentColor = Color.White, // а кнопки и текст делаем белыми
        navigationIcon = { // место, где запустится кнопка
            IconButton( // сама кнопка (вызов меню), вид кнопки получаем из изображения
                onClick = { // при нажатии открываем drawer
                    coroutine.launch { scaffoldState.drawerState.open() } // drawerState - встроенная функция в scaffoldState, open - suspend(приостанавливающая) функция, поэтому запускаем её через корутины
                }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu") // установка изображения кнопки
            }
        },
        actions = { // место, где запустится кнопка
            IconButton( // сама кнопка (лайк)
                onClick = {
                    onFavoriteClick() // запускаем функцию смены title
                }
            ) {
                Icon(
                    /*imageVector = Icons.Default.Favorite*/
                    painter = painterResource(id = R.drawable.like_at_topbar), // установил свое изображение, но оно тоже не совсем белое
                    contentDescription = "Favorite") // установка изображения кнопки
            }
        }
    )
}