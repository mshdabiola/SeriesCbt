/*
 *abiola 2022
 */

package com.mshdabiola.cbtapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.UserData
import com.mshdabiola.cbtapp.MainActivityUiState.Loading
import com.mshdabiola.cbtapp.MainActivityUiState.Success
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.designsystem.string.getByte
import com.mshdabiola.designsystem.string.getFileUri
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class MainAppViewModel(
    userDataRepository: UserDataRepository,
    private val examinationRepository: IExaminationRepository
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

//    init {
//        viewModelScope.launch {
//            val all = examinationRepository.getAll().first()
//            if (all.isEmpty()) {
//                val byte= getByte("files/data/data.db")
//                val files= File.createTempFile("data","db")
//                files.writeBytes(byte)
//                examinationRepository
//                    .import(files.path,"abiola")
//            }
//        }
//    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
