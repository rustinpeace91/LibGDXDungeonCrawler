package dungeon.crawler.GameSystem.SaveGame.Serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object GameSaveJson {

    @JvmStatic
    fun encode(save: GameSave): String {
        return Json.encodeToString(save)
    }
}
