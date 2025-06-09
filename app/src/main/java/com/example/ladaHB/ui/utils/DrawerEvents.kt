package com.example.ladaHB.ui.utils
// drawer events - события, которые будут происходит в drawer, с помощью этого класса будем отслеживать на какую позицию нажал пользователь

// sealed - "запечатанный" - позволяет сгруппировать другие дата-классы
// можно и отдельно сделать дата класс онайтемклик, но это неудобно при расширении программы будет, т.к. если мы захотим еще обрабатывать какие-то события (например кнопка удалить, добавить, и т.д.) то не сможем передать в fun drawerMenu(onEvent: DrawerEvents)
sealed class DrawerEvents {
    data class OnItemClick(val title: String, val index: Int): DrawerEvents()
}
