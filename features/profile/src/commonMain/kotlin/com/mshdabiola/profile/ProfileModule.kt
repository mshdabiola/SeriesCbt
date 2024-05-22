package com.mshdabiola.profile

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel {
        ProfileViewModel()
    }
}
