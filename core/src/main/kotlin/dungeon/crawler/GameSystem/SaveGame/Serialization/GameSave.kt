package dungeon.crawler.GameSystem.SaveGame.Serialization

import kotlinx.serialization.Serializable

@Serializable
data class GameSave(
    val player: PartyCharacterSave,
    val overWorldCoordinatesX: Int,
    val overWorldCoordinatesY: Int,
    val party: List<PartyCharacterSave>,
    val gold: Int
)
