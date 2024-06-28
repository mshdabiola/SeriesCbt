/*
 *abiola 2022
 */

package com.mshdabiola.benchmarks.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.mshdabiola.benchmarks.PACKAGE_NAME
import com.mshdabiola.benchmarks.finish.backToMain
import com.mshdabiola.benchmarks.main.goToQuestionScreen
import com.mshdabiola.benchmarks.question.clickOption
import com.mshdabiola.benchmarks.question.gotoFinishScreen
import com.mshdabiola.benchmarks.startActivity
import org.junit.Rule
import org.junit.Test

class GenerateBaselineProfile {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() =
        baselineProfileRule.collect(PACKAGE_NAME) {
            startActivity()

            goToQuestionScreen()

            clickOption()

            gotoFinishScreen()
            backToMain()
        }
}
