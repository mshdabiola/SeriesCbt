package com.mshdabiola.finish

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val finishModule = module {
    viewModel {
        FinishViewModel(get(), get(),get(),get())
    }
}
