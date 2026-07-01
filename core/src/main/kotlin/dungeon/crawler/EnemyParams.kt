package dungeon.crawler

data class EnemyParams(
    val name: String,
    val toHit: Int,
    val earnedXP: Int,
    val initiative: Int,
    val maxHp: Int,
    val maxMP: Int,
    val hp: Int,
    val mp: Int,
)