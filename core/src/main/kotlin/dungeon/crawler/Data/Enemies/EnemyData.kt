package dungeon.crawler.Data.Enemies;

data class EnemyParams(
    val name: String,
    val id: String,
    val toHit: Int,
    val earnedXP: Int,
    val initiative: Int,
    val maxHp: Int,
    val maxMP: Int,
    val hp: Int,
    val mp: Int,
    val defense: Int
)

class EnemyRegistry {
    private val enemyData = mutableMapOf<String, EnemyParams>();

    fun registerEnemy(id: String, params: EnemyParams) {
        enemyData[id] = params
    }

    fun getEnemyById(id: String): EnemyParams? {
        return enemyData.get(id);
    }

}

object DataInitializer {
    @JvmStatic
    fun initializeEnemyData(): EnemyRegistry {
        val registry = EnemyRegistry()
        registry.registerEnemy(
            "rat",
            EnemyParams(
                "rat",
                "rat",
                2,
                50,
                1,
                16,
                0,
                6,
                0,
                3
            )
        )
        return registry;
    }
}
