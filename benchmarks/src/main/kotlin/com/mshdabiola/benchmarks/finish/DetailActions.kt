/*
 *abiola 2024
 */

package com.mshdabiola.benchmarks.finish

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.backToMain() {
    val selector = By.res("finish:back")

    device.wait(Until.hasObject(selector), 5000)

    val backButton = device.findObject(selector)
    backButton.click()
    // Wait until saved title are shown on screen
}
