package com.mshdabiola.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.mshdabiola.model.ImageUtil
import com.mshdabiola.model.ImageUtil.getGeneralDir
import com.mshdabiola.model.data.Type
import com.mshdabiola.retex.Latex
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import java.io.File

@Composable
fun ItemUi(items: ImmutableList<ItemUiState>, examID: Long) {
    val childModifier = Modifier.fillMaxWidth()

    items.forEach { item ->
        when (item.type) {
            Type.EQUATION -> Box(childModifier, contentAlignment = Alignment.Center) {
                Latex(modifier = Modifier, item.content)
            }

            Type.TEXT -> MarkUpText(modifier = childModifier, text = item.content)


            Type.IMAGE -> {
                Box(childModifier, contentAlignment = Alignment.Center) {
                    ImageUi(
                        Modifier.width(400.dp).aspectRatio(16f/9f),
                        path = getGeneralDir(item.content, examID).path,
                        contentDescription = item.content,
                    )
                }

            }
        }
    }
}


@Composable
 fun ImageUi(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
){
    val filePath = File(path)

    when (filePath.extension) {
        "svg" -> {
            AsyncImage(
                modifier = modifier,
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(path)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = contentDescription,
                contentScale =contentScale
            )

        }


        else -> {
            AsyncImage(
                modifier = modifier,
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(path)
                    .build(),
                contentDescription = contentDescription,
                contentScale =contentScale
            )
        }
    }


}
