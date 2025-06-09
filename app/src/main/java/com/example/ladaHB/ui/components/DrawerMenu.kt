package com.example.ladaHB.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.ladaHB.R
import com.example.ladaHB.ui.theme.BgTransparent
import com.example.ladaHB.ui.utils.DrawerEvents

@Composable
fun DrawerMenu(onEvent: (DrawerEvents) -> Unit)  { // в параметрах передаем класс событий (это абстракция, никакой конкретики) - это будет полезно если мы сделаем в DrawerEvents обработку ещё каких-нибудь событий
    // Unit - обозначаем что функция ничего не вернёт
    Box(modifier = Modifier.fillMaxSize()) { // на весь экран что позволяет drawer
        Image(
            painter = painterResource( // выбираем изображение - фон всего drawer
                id = R.drawable.drawer_background  // путь к изображению
            ),
            contentDescription = "Main Bg Image", // название по умолчанию
            modifier = Modifier.fillMaxSize(), // по всей высоте и ширине
            contentScale = ContentScale.Crop // обрезаем лишнее
        )
        Column(modifier = Modifier.fillMaxSize()) { // по всей высоте и ширине
            Header() // запускаем голову
            Body() { // запускаем тело
                event -> onEvent(event) // передаем выбранное событие наверх - теперь в вызывающий fun DrawerMenu класс, изначально это был MainActivity. а теперь конкретнее: в MainScreen -> Scaffold -> drawerContent -> DrawerMenu
            }
        }
    }
}

@Composable
fun Header() { // голова drawer (ящик) - верхняя часть
    Card( // контейнер card - карточка - он похож на банковскую карточку
        modifier = Modifier // модификатор
            .fillMaxWidth() // на всю ширину
            .height(170.dp) // высота 170 пикс
            .padding(5.dp), // отступ
        shape = RoundedCornerShape(8.dp), // закругления
        border = BorderStroke(1.dp, Color.Black), // рамка
        backgroundColor = Color.Transparent // прозрачный фон
    ) {
        /*Box(
            modifier = Modifier.fillMaxSize(), // растягиваем на весь размер, т.е. бокс на всю карточку
            contentAlignment = Alignment.BottomCenter // Alignment - выравнивание снизу по центру
        ) // НУЖЕН ЕСЛИ ЕСТЬ ЕЩЕ И ТЕКСТ, А Я ЕГО УДАЛИЛ
        {*/
            Image(
                painter = painterResource(id = R.drawable.drawer_header_background), // устанавливаем изображение
                contentDescription = "Header Image", // название по умолчанию
                modifier = Modifier.fillMaxSize(), // во весь экран
                contentScale = ContentScale.Fit // изображение подстраиваем под размер окна
            )
            /*Text(
                text = "-Справочник АвтоВАЗ-",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MainRed)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }*/
    }
}

@Composable
fun Body(onEvent: (DrawerEvents) -> Unit) { // тело drawer (ящик) - нижняя часть. сюда тоже передаем событие
    val list = stringArrayResource(id = R.array.drawer_list) // записываем текст из массива
    LazyColumn(modifier = Modifier.fillMaxSize()) { // "ленивая колонка" на весь размер
        itemsIndexed(list) { index, title -> // читаем текст по индексу - заголовок
            Card(
                modifier = Modifier
                    .fillMaxWidth() // на всю ширину
                    .padding(3.dp), // рамка
                backgroundColor = BgTransparent // цвет фона
            ) {
                Text(
                    text = title, // текст
                    modifier = Modifier
                        .fillMaxWidth() // на всю ширину
                        .clickable {  // будет кликабельным + обработка клика
                            onEvent(DrawerEvents.OnItemClick(title, index))
                            // выбираем нужный обработчик события и передаем наверх в fun DrawerMenu
                        }
                        .padding(10.dp) // рамка в 10 пикс
                        .wrapContentWidth(), // по центру
                    //fontWeight = FontWeight.Bold, // жирный шрифт не хочу
                    color = Color.White, // цвет текста
                    fontStyle = FontStyle(R.font.lada_pragmatica) // шрифт
                )
            }
        }
    }
}