/*
 *abiola 2024
 */

package com.mshdabiola.cbtapp

import android.app.Application
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.model.Writer
import com.mshdabiola.cbtapp.di.appModule
import com.mshdabiola.cbtapp.di.jankStatsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val logger = Logger(
            loggerConfigInit(platformLogWriter(), Writer(this.filesDir)),
            "AndroidLogger",
        )
        val logModule = module {
            single {
                logger
            }
        }

        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            androidContext(this@SkeletonApplication)
            modules(appModule, jankStatsModule)
        }

//        if (packageName.contains("debug")) {
//            Timber.plant(Timber.DebugTree())
//            Timber.e("log on app create")
//        }
    }
}
