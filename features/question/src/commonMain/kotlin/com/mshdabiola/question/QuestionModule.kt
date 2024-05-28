package com.mshdabiola.question

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val questionModule = module {
    viewModel { pa ->
        QuestionViewModel(pa[0], pa[1], pa[2], get(), get(), get(), get())
    }
}
