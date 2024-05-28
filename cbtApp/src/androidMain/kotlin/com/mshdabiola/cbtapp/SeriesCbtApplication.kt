/*
 *abiola 2024
 */

package com.mshdabiola.cbtapp

import android.app.Application
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshabiola.database.util.Constant
import com.mshdabiola.cbtapp.di.appModule
import com.mshdabiola.cbtapp.di.jankStatsModule
import com.mshdabiola.model.parentPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SeriesCbtApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        parentPath =
            this.applicationContext.getDatabasePath(Constant.databaseName).parent?.toString() ?: ""

        val logger = Logger(
            loggerConfigInit(
                platformLogWriter(), // Writer(this.filesDir)
            ),
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
            androidContext(this@SeriesCbtApplication)
            modules(appModule, jankStatsModule, logModule)
        }

//        if (packageName.contains("debug")) {
//            Timber.plant(Timber.DebugTree())
//            Timber.e("log on app create")
//        }
    }
}
