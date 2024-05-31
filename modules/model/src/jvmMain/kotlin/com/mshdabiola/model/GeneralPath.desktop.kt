package com.mshdabiola.model

actual val generalPath: String
    get() = // "${System.getProperty("java.io.tmpdir")}/AppData/Local/cbt"
        System.getProperty("java.io.tmpdir") + "/cbt"
