package com.mshdabiola.cbtapp.di

import com.mshdabiola.data.di.dataModule
import com.mshdabiola.finish.finishModule
import com.mshdabiola.main.mainModule
import com.mshdabiola.profile.profileModule
import com.mshdabiola.question.questionModule
import com.mshdabiola.setting.settingModule
import com.mshdabiola.cbtapp.MainAppViewModel
import com.mshdabiola.stat.statModule
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(dataModule, questionModule, finishModule, profileModule,
        statModule, mainModule, settingModule)
    viewModel { MainAppViewModel(get(),get()) }
}
