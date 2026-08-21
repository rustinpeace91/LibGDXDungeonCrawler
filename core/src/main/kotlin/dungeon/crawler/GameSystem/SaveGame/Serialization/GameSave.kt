package dungeon.crawler.GameSystem.SaveGame.Serialization

import dungeon.crawler.GameSystem.GameBuild
import kotlinx.serialization.Serializable

@Serializable
data class GameSave(
    val player: PartyCharacterSave,
    val isPlayerDead: Boolean,
    val currentMap: String,
    val ScreenID: Int,
    val Bag: ArrayList<String>,
    val overWorldCoordinatesX: Int,
    val overWorldCoordinatesY: Int,
    val party: ArrayList<PartyCharacterSave>,
    val gold: Int
)
