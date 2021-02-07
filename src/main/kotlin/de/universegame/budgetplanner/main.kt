
import androidx.compose.desktop.Window
import de.universegame.budgetplanner.AppView
import de.universegame.budgetplanner.util.components.OneTimeBalanceEntry
import de.universegame.budgetplanner.util.components.YearlyEntries
import de.universegame.budgetplanner.util.components.globalBalanceContainer
import java.awt.image.BufferedImage
import java.time.Year
import javax.imageio.ImageIO
import kotlin.random.Random


fun main() = Window(
    title = "Budget Planner",
    icon = loadImageResource("ic_launcher.png")
)
{
    for( i in 0..100){
        val year = Year.of(Random.nextInt(1990, 2025))
        var t = YearlyEntries(year)
        if(globalBalanceContainer.entries.containsKey(year))
            t = globalBalanceContainer.entries[year]!!
        t.months[Random.nextInt(0, 11)].entries.add(OneTimeBalanceEntry(Random.nextDouble(-500.0, 500.0), "test", 0))
        globalBalanceContainer.entries[year] = t
    }

    AppView()
}

private fun loadImageResource(path: String): BufferedImage {
    val resource = Thread.currentThread().contextClassLoader.getResource(path)
    requireNotNull(resource) { "Resource $path not found" }
    return resource.openStream().use(ImageIO::read)
}