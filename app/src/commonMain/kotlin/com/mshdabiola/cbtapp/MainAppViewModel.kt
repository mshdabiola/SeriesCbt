/*
 *abiola 2022
 */

package com.mshdabiola.cbtapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.mshdabiola.cbtapp.MainActivityUiState.Loading
import com.mshdabiola.cbtapp.MainActivityUiState.Success
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.designsystem.string.getByte
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class MainAppViewModel(
    userDataRepository: UserDataRepository,
    private val examinationRepository: IExaminationRepository,
    private val settingRepository: ISettingRepository,
    private val logger: Logger,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainActivityUiState>(Loading)
    val uiState: StateFlow<MainActivityUiState> = _uiState

    private val _isFinish = MutableStateFlow(false)
    val isFinish: StateFlow<Boolean> = _isFinish

    init {
        viewModelScope.launch {
            settingRepository.currentExam
                .collectLatest { current ->

                    val chooses = current.choose.flatten().all { it > -1 }
                    _isFinish.update {
                        chooses
                    }
                }
        }
        viewModelScope.launch {

            val dbTemp = File.createTempFile("data", "db")
            //  if (dbTemp.exists().not()) {
            val byte = getByte("files/data/data.db")
            dbTemp.writeBytes(byte)
            // }

            val vTemp = File.createTempFile("version", "text")
            // if (vTemp.exists().not()) {
            val byte2 = getByte("files/data/version.txt")

            vTemp.writeBytes(byte2)
            // }

            val openHelper = SQLiteCopyOpenHelper(logger, dbTemp, vTemp)

            openHelper.verifyDatabaseFile()

            userDataRepository
                .userData
                .collectLatest { userdata ->
                    _uiState.update {
                        Success(userdata)
                    }
                }
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
