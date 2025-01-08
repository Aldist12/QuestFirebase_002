package com.example.firebase.depedenciesinjection

import com.example.firebase.repository.MahasiswaRepository
import com.example.firebase.repository.NetworkMahasiswaRepository

interface AppContainer{
    val MahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer : AppContainer{
    private val firebase : FirebaseFirestore = FirebaseFirestore.getInstance()
    override val MahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firebase)
    }

}