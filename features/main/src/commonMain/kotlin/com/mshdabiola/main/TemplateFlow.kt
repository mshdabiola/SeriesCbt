package com.mshdabiola.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TFlow(modifier: Modifier = Modifier) {
    FlowRow(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
//                maxItemsInEachRow = 2,
    ) {
        Column(
            Modifier.background(Color.Green)
                .height(200.dp)
                .widthIn(840.dp)
                .weight(0.7f),
        ) {
        }
        Column(
            Modifier.background(Color.Green)
                .height(200.dp)
//                    .widthIn(min = 200.dp)
                .weight(0.3f),
        ) {
        }
        Column(
            Modifier.background(Color.Green)
                .height(200.dp)
                .widthIn(min = 600.dp)
                .weight(0.7f),
        ) {
        }
    }

    FlowRow(modifier = Modifier.fillMaxSize()) {
        FlowRow(modifier = Modifier.weight(0.7f).width(840.dp)) { // desktop
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .width(300.dp)
                    .height(200.dp)
                    .background(Color.Green),
            ) { // tabp
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .width(300.dp)
                    .height(200.dp)
                    .background(Color.Blue),
            ) {
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .width(300.dp)
                    .height(200.dp)
                    .background(Color.Red),
            ) {
            }
        }
        Column(modifier = Modifier.weight(0.3f).height(200.dp).background(Color.Black)) { // desktop
        }
    }
}
