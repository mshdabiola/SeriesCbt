package com.mshdabiola.cbtapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.sqlite.SQLiteConnection
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.designsystem.drawable.defaultAppIcon
import com.mshdabiola.designsystem.string.appName
import com.mshdabiola.cbtapp.di.appModule
import com.mshdabiola.cbtapp.ui.CbtApp
import com.mshdabiola.database.Callback
import com.mshdabiola.database.callback
import com.mshdabiola.designsystem.string.getByte
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import java.io.File

fun mainApp() {
    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )

        val version = "0.0.1"
        Window(
            onCloseRequest = ::exitApplication,
            title = "$appName v$version",
            icon = defaultAppIcon,
            state = windowState,
        ) {
            CbtApp()
        }
    }
}

fun main() {
    val path = File("${System.getProperty("user.home")}/AppData/Local/hydraulic")
    if (path.exists().not()) {
        path.mkdirs()
    }
    val logger = Logger(
        loggerConfigInit(platformLogWriter(), Writer(path)),
        "DesktopLogger,",
    )
    val logModule = module {
        single {
            logger
        }
    }



//    callback = object : Callback() {
//        override fun onCreate(connection: SQLiteConnection, path: String) {
//            CoroutineScope(Dispatchers.Main).launch {
//                logger.e("onCreate desktop")
//
//                val dbTemp= File.createTempFile("data","db")
//                //  if (dbTemp.exists().not()) {
//                val byte= getByte("files/data/data.db")
//                dbTemp.writeBytes(byte)
//                //}
//
//                val vTemp= File.createTempFile("version","text")
//                // if (vTemp.exists().not()) {
//                val byte2= getByte("files/data/version.txt")
//
//                vTemp.writeBytes(byte2)
//                //}
//
//                val openHelper=SQLiteCopyOpenHelper(logger,dbTemp,vTemp)
//
//                openHelper.verifyDatabaseFile()
//
//            }
//
//        }
//
//        override fun onDestructiveMigration(connection: SQLiteConnection) {
//            logger.e("onDestructiveMigration desktop")
//        }
//
//        override fun onOpen(connection: SQLiteConnection) {
//
//            CoroutineScope(Dispatchers.Main).launch {
//                logger.e("onOpen desktop")
//
//
//                val dbTemp= File.createTempFile("data","db")
//                //  if (dbTemp.exists().not()) {
//                val byte= getByte("files/data/data.db")
//                dbTemp.writeBytes(byte)
//                //}
//
//                val vTemp= File.createTempFile("version","text")
//                // if (vTemp.exists().not()) {
//                val byte2= getByte("files/data/version.txt")
//
//                vTemp.writeBytes(byte2)
//                //}
//
//                val openHelper=SQLiteCopyOpenHelper(logger,dbTemp,vTemp)
//
//                openHelper.verifyDatabaseFile()
//
//
//            }
//
//        }
//
//    }


    try {
        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            modules(
                appModule,
                logModule,
            )
        }

        mainApp()
    } catch (e: Exception) {
        logger.e("crash exceptions", e)
        throw e
    }
}
