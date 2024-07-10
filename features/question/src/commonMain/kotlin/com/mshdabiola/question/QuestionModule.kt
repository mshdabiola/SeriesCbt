package com.mshdabiola.question

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val questionModule = module {
    viewModelOf(::QuestionViewModel)
}
