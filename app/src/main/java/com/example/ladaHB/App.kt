package com.example.ladaHB

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// это класс для работы hilt + указать его в манифесте

@HiltAndroidApp
class App: Application() {
}