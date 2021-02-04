import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

val DarkTheme = darkColors(background = Color(0x2d2d2d))

fun main() = Window(title = "Budget Planner", size = IntSize(300, 300)) {
    val count = remember { mutableStateOf(0) }
    MaterialTheme(
        colors = DarkTheme
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Column(Modifier.fillMaxHeight().width(100.dp), Arrangement.spacedBy(5.dp)) {
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value++
                    }) {
                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
                }
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value = 0
                    }) {
                    Text("Reset")
                }
            }
            Column(Modifier.fillMaxHeight().width(100.dp), Arrangement.spacedBy(5.dp)) {
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value++
                    }) {
                    Text(if (count.value == 0) "Hello Worlds" else "Clicked ${count.value}!")
                }
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value = 0
                    }) {
                    Text("Reset1")
                }
            }
        }
    }
}

private fun loadImageResource(path: String): BufferedImage {
    val resource = Thread.currentThread().contextClassLoader.getResource(path)
    requireNotNull(resource) { "Resource $path not found" }
    return resource.openStream().use(ImageIO::read)
}