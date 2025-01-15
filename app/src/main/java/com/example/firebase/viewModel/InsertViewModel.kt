package com.example.firebase.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.model.Mahasiswa
import com.example.firebase.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel (
    private val mhs: MahasiswaRepository
): ViewModel(){
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent
        )
    }

    fun validateFields(): Boolean{
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if(event.nim.isNotEmpty()) null else "Nim tidak boleh kosong",
            nama = if(event.nama.isNotEmpty()) null else "Nim tidak boleh kosong",
            jenis_kelamin = if(event.jenis_kelamin.isNotEmpty()) null else "Nim tidak boleh kosong",
            alamat = if(event.alamat.isNotEmpty()) null else "Nim tidak boleh kosong",
            kelas = if(event.kelas.isNotEmpty()) null else "Nim tidak boleh kosong",
            angkatan = if(event.angkatan.isNotEmpty()) null else "Nim tidak boleh kosong",
            judul_skripsi = if(event.angkatan.isNotEmpty()) null else "Judul tidak boleh kosong",
            dosen_pembimbing = if(event.angkatan.isNotEmpty()) null else "Nama Dosen tidak boleh kosong",
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMhs(){
        if (validateFields()){
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    mhs.insertMahasiswa(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil disimpan")
                } catch (e: Exception){
                    uiState = FormState.Error("Data gagal disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }
    fun resetForm(){
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    fun resetSnacBarMessage(){
        uiState = FormState.Idle
    }

}

sealed class FormState{
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()

}

data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenis_kelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
    val judul_skripsi: String? = null,
    val dosen_pembimbing: String? = null,
){
    fun isValid(): Boolean {
        return nim == null
                && nama == null
                && jenis_kelamin == null
                && alamat == null
                && kelas == null
                && angkatan == null
                && judul_skripsi == null
                && dosen_pembimbing == null
    }
}

fun MahasiswaEvent.toMhsModel() : Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenis_kelamin = jenis_kelamin,
    alamat = nim,
    kelas = nim,
    angkatan = nim,
    judul_skripsi = nama,
    dosen_pembimbing = nama,

    )

data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenis_kelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val judul_skripsi: String = "",
    val dosen_pembimbing: String = "",
)