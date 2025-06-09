package com.example.ladaHB.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ladaHB.MainViewModel
import com.example.ladaHB.R
import com.example.ladaHB.ui.theme.ColorGr
import com.example.ladaHB.ui.theme.ColorLikeBackground
import com.example.ladaHB.ui.theme.ColorRed
import com.example.ladaHB.ui.utils.ListItem

// класс в котором описывается как будет выглядеть один item в списке LazyColumn
@Composable
fun MainListItem(
    mainViewModel: MainViewModel = hiltViewModel(), // писец. если получение hiltViewModel поставить третьим, сразу выбрасывает ошибку в MainScreen
    item: ListItem,
    onClick: (ListItem) -> Unit
    ) {
    Card( // контейнер card - карточка
        modifier = Modifier // модификатор
            .fillMaxWidth() // на всю ширину
            .height(310.dp) // высота 170 пикс / 300
            .padding(5.dp) // отступ
            .clickable { // делаем кликабельным, чтобы открыть нужную html
                onClick(item) // передаем запуск в MainScreen
            },
        shape = RoundedCornerShape(8.dp), // закругления
        border = BorderStroke(1.dp, Color.Black), // рамка
        backgroundColor = Color.White // прозрачный фон
    ) {
        /*Box(
            modifier = Modifier.fillMaxSize(), // растягиваем на весь размер, т.е. бокс на всю карточку
            contentAlignment = Alignment.BottomCenter // Alignment - выравнивание снизу по центру
        )
        {

            AssetImage( // загружаем изображение с помощью созданного ниже метода
                imageName = item.imageName,
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize() // в случае если в AssetImage не нужно задавать разный модифаер, можно там указать напрямую, не передавая этот параметр туда
            )
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }*/
        ConstraintLayout( // на этапе добавления лайка к контенту убираем бокс и включаем конст лэй
            modifier = Modifier.fillMaxSize(), // растягиваем на весь размер, т.е. бокс на всю карточку
        )
        {
            val(image, text, favoriteButton) = createRefs() // даем названия нашим элементам, ниже их используем
            AssetImage( // загружаем изображение с помощью созданного ниже метода
                imageName = item.imageName,
                contentDescription = item.title, // в случае если в AssetImage не нужно задавать разный модифаер, можно там указать напрямую, не передавая этот параметр туда
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(image) {// растягиваем на все окно, прикрепляем края
                        top.linkTo(parent.top) // со всех 4х сторон прикрепляем к родительскому элементу
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp)
                    .constrainAs(text) {// а текст прикрепляем только с 3-х сторон
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            IconButton( // и настраиваем кнопку лайк
                onClick = {
                    mainViewModel.insertItem(
                        item.copy(isFavorite = !item.isFavorite) // с помощью этой команды записываем в избранное и удаляем + в Dao помогает стратегия @Insert(onConflict = OnConflictStrategy.REPLACE) - если возникает конфликтто заменить элемент
                    )
                },
                modifier = Modifier.constrainAs(favoriteButton) {
                    top.linkTo(parent.top) // располагаем его сверху справа у каждого элемента
                    end.linkTo(parent.end)
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.favorite_like_image), // это путь к картинке лайк, можно выбрать любую естественно
                    // tint = ColorRed, // цвет значка не подтягивается, назначаем отдельно // меняем цвет значка если добавили/удалили из избранного ->
                    tint = if (item.isFavorite) ColorRed else ColorGr,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .clip(CircleShape) // клипса (круглая форма)
                        .background(ColorLikeBackground) // цвет фона рамки вокруг лайка
                        .padding(5.dp) // отступ
                )
            }
        }
    }
}
@Composable
fun AssetImage(imageName: String, contentDescription: String, modifier: Modifier){ // метод поиска нужного изображения
    val context = LocalContext.current // получаем контекст
    val assetManager = context.assets // получаем ассет менеджер
    val inputStream = assetManager.open(imageName) // получаем входящий поток
    val bitMap = BitmapFactory.decodeStream(inputStream) // получаем битмап чтобы передать в Image
    Image(bitmap = bitMap.asImageBitmap(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier // можно было бы и напрямую написать Modifier.fillMaxSize(), но мы хотим переиспользовать этот метод в InfoScreen, а там не нужно открывать на весь экран, нужно только сверху изображение (но мне не нужно, я этот код закомментирую)
        )

}