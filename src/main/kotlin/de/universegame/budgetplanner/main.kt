
import androidx.compose.desktop.Window
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import de.universegame.budgetplanner.views.AppView
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.components.loadBalanceContainer
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

fun main() = Window(
    title = "Budget Planner",
    icon = loadImageResource("ic_launcher.png"),
    size = IntSize(1300, 600),
    centered = true,
)
{
    val settings: Settings = Settings()
    val balanceContainer = remember { mutableStateOf(loadBalanceContainer(settings.dataFileName, settings.jsonSerializer)) }
    AppView(balanceContainer, settings)
}

private fun loadImageResource(path: String): BufferedImage {
    val resource = Thread.currentThread().contextClassLoader.getResource(path)
    requireNotNull(resource) { "Resource $path not found" }
    return resource.openStream().use(ImageIO::read)
}