package dungeon.crawler.Data.Enemies



data class EnemySpriteParams(
    val id: String,
    val frame1: String,
    val frame2: String,
    val width: Float,
    val height: Float
)

class EnemySpriteRegistry{
    val enemySpriteMap = mutableMapOf<String, EnemySpriteParams>();

    init {
        initializeSpriteData();
    }

    fun initializeSpriteData() {
        enemySpriteMap["rat"]  = EnemySpriteParams(
            id = "rat",
            frame1 = "Sprites/Enemies/ratanim/testrat1.png",
            frame2 = "Sprites/Enemies/ratanim/testrat2.png",
            width = 100f,
            height = 100f
        )

        enemySpriteMap["skeleton"]  = EnemySpriteParams(
            id = "skeleton",
            frame1 = "Sprites/Enemies/skellianim/skelli1.png",
            frame2 = "Sprites/Enemies/skellianim/skelli2.png",
            width = 300f,
            height = 300f
        )
        enemySpriteMap["spider"]  = EnemySpriteParams(
            id = "spider",
            frame1 = "Sprites/Enemies/spooderanim/frame1.png",
            frame2 = "Sprites/Enemies/spooderanim/frame2.png",
            width = 200f,
            height = 150f
        )
    }

}

//class EnemyRegistry {
//    private val enemyData = mutableMapOf<String, EnemyParams>();
//
//    fun registerEnemy(id: String, params: EnemyParams) {
//        enemyData[id] = params
//    }
//
//    fun getEnemyById(id: String): EnemyParams? {
//        return enemyData.get(id);
//    }
//
//}
//
//object DataInitializer {
//    @JvmStatic
//    fun initializeEnemyData(): EnemyRegistry {
//        val registry = EnemyRegistry()
//        registry.registerEnemy(
//            "rat",
//            EnemyParams(
//                "rat",
//                "rat",
//                2,
//                50,
//                1,
//                16,
//                0,
//                16,
//                0,
//                3,
//                Pair(1, 10),
//                "rat takes a bite"
//            )
//        )
//        registry.registerEnemy(
//            "spider",
//            EnemyParams(
//                "spider",
//                "spider",
//                2,
//                100,
//                2,
//                30,
//                0,
//                30,
//                0,
//                5,
//                Pair(2, 20),
//                "spider takes a bite"
//            )
//        )
//        registry.registerEnemy(
//            "skeleton",
//            EnemyParams(
//                "skeleton",
//                "skeleton",
//                3,
//                200,
//                2,
//                50,
//                0,
//                50,
//                0,
//                5,
//                Pair(4, 50),
//                "skeleton takes a bite"
//            )
//        )
//        return registry;
//    }
//}
