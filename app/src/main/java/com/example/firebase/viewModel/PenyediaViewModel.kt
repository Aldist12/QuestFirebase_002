package com.example.firebase.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebase.MahasiswaApplication

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiMahasiswa().container.MahasiswaRepository) }
        initializer { InsertViewModel(aplikasiMahasiswa().container.MahasiswaRepository) }
    }
}

fun CreationExtras.aplikasiMahasiswa(): MahasiswaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplication)