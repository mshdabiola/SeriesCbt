/*
 *abiola 2022
 */

package com.mshdabiola.benchmarks.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.mshdabiola.benchmarks.PACKAGE_NAME
import com.mshdabiola.benchmarks.startActivity
import org.junit.Rule
import org.junit.Test

class GenerateBaselineProfile {
    @get:Rule val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() =
        baselineProfileRule.collect(PACKAGE_NAME) {
            startActivity()

//            goToQuestionScreen()
//
//            repeat(2) {
//               clickOption()
//            }
//
//           gotoFinishScreen()
//            backToMain()
        }
}
