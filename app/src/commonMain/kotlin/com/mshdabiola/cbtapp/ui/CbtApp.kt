/*
 *abiola 2022
 */

package com.mshdabiola.cbtapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.cbtapp.MainActivityUiState
import com.mshdabiola.cbtapp.MainAppViewModel
import com.mshdabiola.cbtapp.navigation.SkNavHost
import com.mshdabiola.designsystem.component.CbtBackground
import com.mshdabiola.designsystem.component.CbtGradientBackground
import com.mshdabiola.designsystem.theme.CbtTheme
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.ui.CommonBar
import com.mshdabiola.ui.CommonNavigation
import com.mshdabiola.ui.CommonRail
import com.mshdabiola.ui.SplashScreen
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.correct
import com.mshdabiola.ui.semanticsCommon
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun CbtApp() {
    val windowSizeClass = calculateWindowSizeClass()
    val appState = rememberCbtAppState(
        windowSizeClass = windowSizeClass,
    )
    val shouldShowGradientBackground = false

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycleCommon()
    val darkTheme = shouldUseDarkTheme(uiState)
    val isFinish = viewModel.isFinish.collectAsStateWithLifecycleCommon()

    CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
        CbtTheme(
            darkTheme = darkTheme,

            disableDynamicTheming = shouldDisableDynamicTheming(uiState),
        ) {
            CbtBackground {
                CbtGradientBackground(
                    gradientColors = if (shouldShowGradientBackground) {
                        LocalGradientColors.current
                    } else {
                        GradientColors()
                    },
                ) {
                    if (uiState == MainActivityUiState.Loading) {
                        SplashScreen()
                    } else {
                        val snackbarHostState = remember { SnackbarHostState() }

                        Row {
                            if (appState.shouldShowNavRail) {
                                CommonRail(
                                    modifier = Modifier.width(100.dp).fillMaxHeight(),
                                    currentNavigation = appState.currentDestination?.route
                                        ?: "",
                                    onNavigate = appState::onNavigate,

                                )
                            }
                            PermanentNavigationDrawer(
                                drawerContent = {
                                    if (appState.shouldShowDrawer) {
                                        CommonNavigation(
                                            modifier = Modifier.width(300.dp).fillMaxHeight(),
                                            currentNavigation = appState.currentDestination?.route
                                                ?: "",
                                            onNavigate = appState::onNavigate,
                                        )
                                    }
                                },

                            ) {
                                Scaffold(
                                    modifier = Modifier.semanticsCommon {},
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onBackground,
                                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                                    snackbarHost = { SnackbarHost(snackbarHostState) },
                                    bottomBar = {
                                        if (appState.shouldShowBottomBar) {
                                            CommonBar(
                                                currentNavigation = appState.currentDestination?.route
                                                    ?: "",
                                            ) { appState.onNavigate(it) }
                                        }
                                        if (appState.shouldShowGeneralBottomBar) {
                                            BottomAppBar(
                                                floatingActionButton = {
                                                    if (appState.showFab) {
                                                        ExtendedFloatingActionButton(
                                                            containerColor = if (appState.isQuestion && isFinish.value) {
                                                                correct()
                                                            } else {
                                                                FloatingActionButtonDefaults.containerColor
                                                            },
                                                            onClick = appState::onFabClick,
                                                        ) {
                                                            Text(text = appState.fabName)
                                                        }
                                                    }
                                                },
                                                actions = {
                                                    IconButton(onClick = { appState.navController.popBackStack() }) {
                                                        Icon(Icons.Default.ArrowBackIosNew, "back")
                                                    }
                                                },
                                            )
                                        }
                                    },
                                    floatingActionButton = {
                                        if (appState.showFab && appState.showTopBar) {
                                            ExtendedFloatingActionButton(
                                                containerColor = if (appState.isQuestion && isFinish.value) {
                                                    correct()
                                                } else {
                                                    FloatingActionButtonDefaults.containerColor
                                                },
                                                onClick = appState::onFabClick,
                                            ) {
                                                Text(text = appState.fabName)
                                            }
                                        }
                                    },
                                    topBar = {
                                        if (appState.showTopBar) {
                                            TopAppBar(
                                                navigationIcon = {
                                                    if (!appState.isMain) {
                                                        IconButton(onClick = { appState.navController.popBackStack() }) {
                                                            Icon(
                                                                Icons.Default.ArrowBackIosNew,
                                                                "back",
                                                            )
                                                        }
                                                    }
                                                },
                                                title = { Text("Series cbt") },
                                            )
                                        }
                                    },

                                ) { padding ->

                                    Column(
                                        Modifier
                                            .fillMaxSize()
                                            .padding(padding)
                                            .consumeWindowInsets(padding)
                                            .windowInsetsPadding(
                                                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
                                            ),
                                    ) {
                                        SkNavHost(
//                                                    modifier = Modifier.weight(0.7f),
                                            appState = appState,
                                            onShowSnackbar = { message, action ->
                                                snackbarHostState.showSnackbar(
                                                    message = message,
                                                    actionLabel = action,
                                                    duration = SnackbarDuration.Short,
                                                ) == SnackbarResult.ActionPerformed
                                            },
                                        )
//                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun chooseTheme(
    uiState: MainActivityUiState,
): ThemeBrand = when (uiState) {
    MainActivityUiState.Loading -> ThemeBrand.DEFAULT
    is MainActivityUiState.Success -> uiState.userData.themeBrand
}

@Composable
private fun shouldUseAndroidTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> when (uiState.userData.themeBrand) {
        ThemeBrand.DEFAULT -> false
        ThemeBrand.GREEN -> true
    }
}

@Composable
private fun chooseContrast(
    uiState: MainActivityUiState,
): Contrast = when (uiState) {
    MainActivityUiState.Loading -> Contrast.Normal
    is MainActivityUiState.Success -> uiState.userData.contrast
}

@Composable
private fun shouldDisableDynamicTheming(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> !uiState.userData.useDynamicColor
}

@Composable
fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success -> when (uiState.userData.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }
