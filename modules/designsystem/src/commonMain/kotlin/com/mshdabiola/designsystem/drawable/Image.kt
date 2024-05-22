package com.mshdabiola.designsystem.drawable

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import seriescbt.modules.designsystem.generated.resources.Res
import seriescbt.modules.designsystem.generated.resources.icon

val defaultAppIcon
    @Composable
    get() = painterResource(Res.drawable.icon)

// imageResource(Res.drawable.icon)
