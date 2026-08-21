package dungeon.crawler.GameSystem.SaveGame.Serialization

import dungeon.crawler.GameSystem.Character.Condition
import dungeon.crawler.GameSystem.Character.Stance
import kotlinx.serialization.Serializable

@Serializable
data class PartyCharacterSave(
    val name: String,
    val level: Int,
    val xp: Int,
    val strength: Int,
    val agility: Int,
    val intelligence: Int,
    val perception: Int,
    val isHero: Boolean,
    val toHit: Int,
    val charClass: String,
    val equipment: Map<String, String>,
    val maxHp: Int,
    val maxMP: Int,
    val hp: Int,
    val mp: Int,
    val stance: Stance,
    val conditions: ArrayList<Condition>,
    val isDead: Boolean,
    val inventory: ArrayList<String>,
    val spells: ArrayList<String>
)
