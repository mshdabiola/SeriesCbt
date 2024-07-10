package com.mshdabiola.stat

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val statModule = module {
    viewModelOf(::StatViewModel)
}
