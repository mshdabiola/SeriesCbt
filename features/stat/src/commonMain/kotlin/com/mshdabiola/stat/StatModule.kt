package com.mshdabiola.stat

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val statModule = module {
    viewModel {
        StatViewModel()
    }
}
