package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import java.io.ByteArrayInputStream
import java.io.IOException

@OptIn(ExperimentalAnimationApi::class, InternalAPI::class)
@Composable
fun AsyncImage(
    modifier: Modifier,
    url: String,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillHeight,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    imageTransformation: ImageTransformation = ImageTransformation.Rectangle
) {
    var img: ImageBitmap? by remember {
        mutableStateOf(null)
    }

    val imageBitmap: ImageBitmap? by produceState(img) {
        if (value == null) {
            try {
                value = HttpClient(CIO).use {
                    val bytes = it.get(url).content.toByteArray()
                    val stream = ByteArrayInputStream(bytes)
                    stream
                }.use(::loadImageBitmap).also { img = it }
            } catch (e: IOException) {
                println(e.cause?.message)
            }
        }
    }

    AnimatedVisibility(
        visible = imageBitmap != null,
        enter = fadeIn(animationSpec = tween(750)),
        exit = fadeOut(animationSpec = tween(750))
    ) {
        Image(
            modifier = modifier.clip(
                shape = when (imageTransformation) {
                    ImageTransformation.Rectangle -> RectangleShape
                    ImageTransformation.Circle -> CircleShape
                    else -> RoundedCornerShape(imageTransformation.radius)
                }
            ),
            bitmap = imageBitmap!!,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            colorFilter = colorFilter,
            alpha = alpha
        )
    }
}

/**
 * Для трансформации изображения
 */
enum class ImageTransformation(
    var radius: Int = 0
) {
    Circle,
    Rectangle,
    RoundedCorner(
        radius = 8
    )
}