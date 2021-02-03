import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.ui.window.Notifier
import java.awt.Color
import java.awt.image.BufferedImage

fun main() {
    val message = "Some message!"
    val notifier = Notifier()
    Window(
        icon = getMyAppIcon()
    ) {
        Column {
            Button(onClick = { notifier.notify("Notification.", message) }) {
                Text(text = "Notify")
            }
            Button(onClick = { notifier.warn("Warning.", message) }) {
                Text(text = "Warning")
            }
            Button(onClick = { notifier.error("Error.", message) }) {
                Text(text = "Error")
            }
        }
    }
}

fun getMyAppIcon() : BufferedImage {
    val size = 256
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    graphics.setColor(Color.green)
    graphics.fillOval(size / 4, 0, size / 2, size)
    graphics.setColor(Color.blue)
    graphics.fillOval(0, size / 4, size, size / 2)
    graphics.setColor(Color.red)
    graphics.fillOval(size / 4, size / 4, size / 2, size / 2)
    graphics.dispose()
    return image
}