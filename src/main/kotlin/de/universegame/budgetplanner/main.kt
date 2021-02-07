import androidx.compose.desktop.Window
import de.universegame.budgetplanner.AppView
import java.awt.image.BufferedImage
import javax.imageio.ImageIO


fun main() = Window(
    title = "Budget Planner",
    icon = loadImageResource("ic_launcher.png")
)
{
    AppView()
}

private fun loadImageResource(path: String): BufferedImage {
    val resource = Thread.currentThread().contextClassLoader.getResource(path)
    requireNotNull(resource) { "Resource $path not found" }
    return resource.openStream().use(ImageIO::read)
}