package com.mshdabiola.finish

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val finishModule = module {
    viewModelOf(::FinishViewModel)
}
