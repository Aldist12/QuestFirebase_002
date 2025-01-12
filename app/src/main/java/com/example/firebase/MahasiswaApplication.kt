package com.example.firebase

import android.app.Application
import com.example.firebase.depedenciesinjection.AppContainer
import com.example.firebase.depedenciesinjection.MahasiswaContainer

class MahasiswaApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}