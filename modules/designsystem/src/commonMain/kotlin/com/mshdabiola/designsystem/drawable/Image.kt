package com.mshdabiola.designsystem.drawable

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import seriescbt.modules.designsystem.generated.resources.Res
import seriescbt.modules.designsystem.generated.resources.icon
import seriescbt.modules.designsystem.generated.resources.modules_ui_cbt_layer_1
import seriescbt.modules.designsystem.generated.resources.modules_ui_cbt_layer_2
import seriescbt.modules.designsystem.generated.resources.modules_ui_cbt_layer__1

val defaultAppIcon
    @Composable
    get() = painterResource(Res.drawable.icon)

// imageResource(Res.drawable.icon)

val layer2
    @Composable
    get() = painterResource(Res.drawable.modules_ui_cbt_layer_2)

val layer1
    @Composable
    get() = painterResource(Res.drawable.modules_ui_cbt_layer_1)

val layer3
    @Composable
    get() = painterResource(Res.drawable.modules_ui_cbt_layer__1)
