package com.example.firebase.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.model.Mahasiswa
import com.example.firebase.repository.MahasiswaRepository
import kotlinx.coroutines.launch


class DetailViewModel(
    private val mhsRepository: MahasiswaRepository
) : ViewModel() {
    var uiState by mutableStateOf(DetailUiState())
        private set

    fun fetchDetailMahasiswa(nim: String) {
        viewModelScope.launch {
            uiState = DetailUiState(isLoading = true)
            try {
                val mahasiswa = mhsRepository.getMahasiswaById(nim)
                uiState = DetailUiState(detailUiEvent = mahasiswa.toDetailEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Mahasiswa.toDetailEvent(): InsertUiEvent {
    return InsertUiEvent(
        nim = nim,
        nama = nama,
        alamat = alamat,
        jenisKelamin = jenis_kelamin,
        kelas = kelas,
        angkatan = angkatan,
        judulSkripsi = judul_skripsi,
        dosenPembimbing = dosen_pembimbing,
    )
}
