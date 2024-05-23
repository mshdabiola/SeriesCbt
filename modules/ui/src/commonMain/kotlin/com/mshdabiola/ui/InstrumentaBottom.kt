package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.state.InstructionUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionBottomSheet(
    instructionUiState: InstructionUiState?,
    onDismissRequest: () -> Unit = {},
) {
    if (instructionUiState != null) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ItemUi(items = instructionUiState.content, examID = instructionUiState.examId)
            }
        }
    }
}
