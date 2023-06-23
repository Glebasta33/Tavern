package components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayerAnswerButton(
    text: String,
    onAnswerButtonClicked: (String) -> Unit
) {
    Button(
        onClick = { onAnswerButtonClicked.invoke(text) },
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow.copy(0.1f))
    ) {
        Text(text, color = Color.LightGray)
    }
}