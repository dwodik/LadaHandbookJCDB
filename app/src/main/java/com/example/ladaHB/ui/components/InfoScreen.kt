package com.example.ladaHB.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ladaHB.ui.utils.ListItem

// класс, выдающий отображение item
@Composable
fun InfoScreen(item: ListItem) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp), // закругления
        shape = RoundedCornerShape(10.dp) // shape - форма
    ) {
        Column(modifier = Modifier.fillMaxSize()) { // колонна, вверх помещаем изображения (я его уберу)
            /*AssetImage(imageName = item.imageName,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
            )*/
        HtmlLoader(htmlName = item.htmlName) // и указываем с помощью ниже написанного метода нужный html файл
        }
    }
}

@Composable
fun HtmlLoader(htmlName: String) { // метод запуска html

        AndroidView(factory = { // запускаем AndroidView - с помощью него можно показать элементы типо TextView, WebView, и т.д.
        WebView(it).apply { // запускаем webView и настраиваем его
            webViewClient = WebViewClient()
            settings.javaScriptEnabled
            settings.setSupportZoom(true)
            settings.displayZoomControls

            //loadData(htmlString, "text/html", "utf-8")
            loadUrl("file:///android_asset/html/$htmlName")
            // и соответственно загружаем данные из string, указываем путь и кодировку
        }
    }
    )
}

