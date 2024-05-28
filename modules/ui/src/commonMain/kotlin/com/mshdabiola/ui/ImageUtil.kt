package com.mshdabiola.ui

import com.mshdabiola.designsystem.string.getByte
import com.mshdabiola.model.generalPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object ImageUtil {

    suspend fun newPath(extension: String, examId: Long): File {
        val time = System.currentTimeMillis()
        // val extension = if (extension == "svg") "svg" else extension
        return (getImageFile("$time.$extension"))
    }

    suspend fun getImageFile(name: String): File {
        val image = File(generalPath, "image/$name")
        println("get image $name")

        if (image.parentFile.exists().not()) {
            image.parentFile.mkdirs()
        }
        if (image.exists().not()) {
            val byte = getByte("files/image/$name")
            withContext(Dispatchers.IO) {
                image.outputStream().write(byte)
            }
        }
        return image
    }
}
