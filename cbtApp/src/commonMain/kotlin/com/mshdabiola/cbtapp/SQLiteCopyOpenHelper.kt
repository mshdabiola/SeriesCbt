package com.mshdabiola.cbtapp


import co.touchlab.kermit.Logger
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.Security
import com.mshdabiola.database.generalPath
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Callable
import kotlin.io.path.inputStream

/**
 * An open helper that will copy & open a pre-populated database if it doesn't exist in internal
 * storage. Only destructive migrations are supported, so it is highly suggested to use this as a
 * read-only database.
 *
 * https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-release/room/runtime/src/main/java/androidx/room/SQLiteCopyOpenHelper.java
 */
class SQLiteCopyOpenHelper(
    private val logger :Logger,
    private val dataFile:File,
    private val versionFile:File,
) {

    private var verified = false

    private val key = "abiola"//BuildConfig.store_key
//        if (BuildConfig.DEBUG)
//        BuildConfig.store_key
//    else
//        BuildConfig.store_key

     fun verifyDatabaseFile() {
        val databaseName = Constant.databaseName
        val databaseFilePath = generalPath//context.getDatabasePath(databaseName)
        val databaseFile =File(databaseFilePath,databaseName)
//        val lockChannel =
//            FileOutputStream(File(databaseFilePath, "$databaseName.lck")).channel
        try {
            // Acquire a file lock, this lock works across threads and processes, preventing
            // concurrent copy attempts from occurring.
          //  lockChannel.tryLock()

            if (!databaseFile.exists()) {
                try {
                    // No database file found, copy and be done.
                    copyDatabaseFile(databaseFile)
                    return
                } catch (e: IOException) {
                    throw RuntimeException("Unable to copy database file.", e)
                }
            }

            // A database file is present, check if we need to re-copy it.
            val currentVersion = try {
                readVersion()
            } catch (e: IOException) {
                logger.w("Unable to read database version.",e)
                return
            }
            val oldVersion =
                try {
                    File(databaseFile.parent, "version.txt")
                        .inputStream()
                        .reader()
                        .readText()
                        .toInt()
                } catch (e: IOException) {
                    logger.w("Unable to read database version.", e)

                    0
                }

            logger.e("current verson is $currentVersion")
            logger.e("old verson is $oldVersion")

            if (currentVersion == oldVersion) {
                return
            }

            // Always overwrite, we don't support migrations
            if (databaseFile.delete()) {
                try {
                    copyDatabaseFile(databaseFile)
                } catch (e: IOException) {
                    // We are more forgiving copying a database on a destructive migration since
                    // there is already a database file that can be opened.
                    logger.w("Unable to copy database file.",e)

                }
            } else {
                logger.w("Failed to delete database file ($databaseName) for a copy destructive migration.", )

            }
        } finally {
            try {
               // lockChannel.close()
            } catch (ignored: IOException) {
            }
        }
    }

    /**
     * Reads the user version number out of the database header from the given file.
     *
     * @param databaseFile the database file.
     * @return the database version
     * @throws IOException if something goes wrong reading the file, such as bad database header or
     * missing permissions.
     *
     * @see <a href="https://www.sqlite.org/fileformat.html#user_version_number">User Version
     * Number</a>.
     */
    @Throws(IOException::class)
    private fun readVersion(): Int {
        try {
            return versionFile
                .reader()
                .readText()
                .toInt()
            // .toIntOrNull() ?: 1
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    private fun copyDatabaseFile(destinationFile: File) {
        if (destinationFile.exists().not()){
            destinationFile.parentFile.mkdirs()
            destinationFile.createNewFile()
        }
        Security.decode(dataFile.inputStream(), destinationFile.outputStream(), key)
        val versionOutput = File(destinationFile.parent, "version.txt").outputStream()
        versionOutput.write(versionFile.readBytes())
        versionOutput.close()

        File(destinationFile.parent,"${Constant.databaseName}-wal").outputStream().write(byteArrayOf())
        logger.e("finish copying")
//
//        // An intermediate file is used so that we never end up with a half-copied database file
//        // in the internal directory.
//        val intermediateFile = File.createTempFile(
//            "sqlite-copy-helper", ".tmp", context.cacheDir
//        )
//
//
//        intermediateFile.deleteOnExit()
// //        input.source().use { a ->
// //            intermediateFile.sink().buffer().use { b -> b.writeAll(a) }
// //        }
//        versionOutput.use { out ->
//            context.assets.open("version.txt").use {
//                out.write(it.readBytes())
//            }
//        }
//        Security.decode(input, FileOutputStream(intermediateFile), key)
//
//
//        val parent = destinationFile.parentFile
//        if (parent != null && !parent.exists() && !parent.mkdirs()) {
//            throw IOException("Failed to create directories for ${destinationFile.absolutePath}")
//        }
//
//        if (!intermediateFile.renameTo(destinationFile)) {
//            throw IOException("Failed to move intermediate file (${intermediateFile.absolutePath}) to destination (${destinationFile.absolutePath}).")
//        }
    }



}
