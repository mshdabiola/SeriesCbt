package com.mshdabiola.designsystem.string

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import seriescbt.modules.designsystem.generated.resources.Res
import seriescbt.modules.designsystem.generated.resources.app_name
import seriescbt.modules.designsystem.generated.resources.modules_ui_cbt_exam_part
import seriescbt.modules.designsystem.generated.resources.modules_ui_cbt_sections
import seriescbt.modules.designsystem.generated.resources.modules_ui_cbt_subject
import seriescbt.modules.designsystem.generated.resources.modules_ui_cbt_type

val appName
    @Composable
    get() = stringResource(Res.string.app_name)

val subject
    @Composable
    get() = stringResource(Res.string.modules_ui_cbt_subject)

val type
    @Composable
    get() = stringResource(Res.string.modules_ui_cbt_type)

val examPart
    @Composable
    get() = stringArrayResource(Res.array.modules_ui_cbt_exam_part).toTypedArray()

val sections
    @Composable
    get() = stringArrayResource(Res.array.modules_ui_cbt_sections).toTypedArray()

@OptIn(ExperimentalResourceApi::class)
fun getFileUri(fileName: String) = Res.getUri(fileName)

@OptIn(ExperimentalResourceApi::class)
suspend fun getByte(fileName: String) = Res.readBytes(fileName)
