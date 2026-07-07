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
    val defense: Int,
    val attackDamage: Pair<Int, Int>,
    val attackText: String
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
                16,
                0,
                3,
                Pair(1, 10),
                "rat takes a bite"
            )
        )
        registry.registerEnemy(
            "spider",
            EnemyParams(
                "spider",
                "spider",
                2,
                100,
                2,
                30,
                0,
                30,
                0,
                5,
                Pair(2, 20),
                "spider takes a bite"
            )
        )
        registry.registerEnemy(
            "skeleton",
            EnemyParams(
                "skeleton",
                "skeleton",
                3,
                200,
                2,
                50,
                0,
                50,
                0,
                5,
                Pair(4, 50),
                "skeleton takes a bite"
            )
        )
        return registry;
    }
}
