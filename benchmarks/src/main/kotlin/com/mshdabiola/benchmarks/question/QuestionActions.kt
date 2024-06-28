/*
 *abiola 2024
 */

package com.mshdabiola.benchmarks.question

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.clickOption() {
    val selector = By.res("question:option")

    device.wait(Until.hasObject(selector), 5000)

    val backButton = device.findObject(selector)
    backButton.click()
    // Wait until saved title are shown on screen
}

fun MacrobenchmarkScope.gotoFinishScreen() {
    val submitSelector = By.res("question:submit")

    device.wait(Until.hasObject(submitSelector), 5000)

    val submitButton = device.findObject(submitSelector)

    submitButton.click()

    // Wait until saved title are shown on screen
}
