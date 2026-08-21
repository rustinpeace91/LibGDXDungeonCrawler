package dungeon.crawler.GameSystem.SaveGame.Serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object GameSaveJson {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @JvmStatic
    fun encode(save: GameSave): String {
        return json.encodeToString(save)
    }

    @JvmStatic
    fun decode(data: String): GameSave {
        return json.decodeFromString<GameSave>(data)
    }
}
