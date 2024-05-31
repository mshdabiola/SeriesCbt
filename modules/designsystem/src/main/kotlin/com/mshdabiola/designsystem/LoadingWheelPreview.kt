package com.mshdabiola.designsystem

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.CbtLoadingWheel
import com.mshdabiola.designsystem.component.CbtOverlayLoadingWheel
import com.mshdabiola.designsystem.theme.CbtTheme

@ThemePreviews
@Composable
fun NiaLoadingWheelPreview() {
    CbtTheme {
        Surface {
            CbtLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}

@ThemePreviews
@Composable
fun NiaOverlayLoadingWheelPreview() {
    CbtTheme {
        Surface {
            CbtOverlayLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}
