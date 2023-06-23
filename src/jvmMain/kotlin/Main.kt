import StateHolder._state
import StateHolder.state
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.AsyncImage
import components.PlayerAnswerButton
import components.Replica
import data.model.ReplicaModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.swing.text.StyleConstants.ColorConstants

@Composable
@Preview
fun UI(
    dialogState: StateFlow<List<ReplicaModel>>,
    onAnswerButtonClicked: (String) -> Unit
) {

    val state by dialogState.collectAsState()

    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxSize().background(Color.DarkGray),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                url = TavernCharacterImageUrl,
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier.weight(1f).padding(16.dp)
            ) {
                state.forEach {
                    Replica(it.isLeft, it.text)
                }
                Spacer(Modifier.height(20.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    item {
                        PlayerAnswerButton(
                            text = "Привет",
                            onAnswerButtonClicked = onAnswerButtonClicked
                        )
                    }
                    item {
                        PlayerAnswerButton(
                            text = "Как дела?",
                            onAnswerButtonClicked = onAnswerButtonClicked
                        )
                    }
                }
            }
        }

    }
}


var dialogData = mutableListOf(
    ReplicaModel(isLeft = true, "Здорова, бродяга"),
    ReplicaModel(isLeft = false, "Здорова, Торговец"),
    ReplicaModel(isLeft = true, "Чем-нибудь могу помочь?"),

    )

object StateHolder {
    val _state: MutableStateFlow<List<ReplicaModel>> = MutableStateFlow(dialogData)
    val state: StateFlow<List<ReplicaModel>> = _state.asStateFlow()
}

fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        UI(
            dialogState = state,
            onAnswerButtonClicked = { answerText ->
                val newDialog = dialogData.toMutableList()
                newDialog.add(ReplicaModel(false, answerText))
                _state.value = newDialog
                dialogData = newDialog
            }
        )
    }

}

private const val TavernCharacterImageUrl =
    "https://imagedelivery.net/9sCnq8t6WEGNay0RAQNdvQ/UUID-cl90hcu2v8679809tqyxmmvg4oj/public"
