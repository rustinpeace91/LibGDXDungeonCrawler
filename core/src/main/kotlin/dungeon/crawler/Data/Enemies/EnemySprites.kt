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
        enemySpriteMap["boss"]  = EnemySpriteParams(
            id = "boss",
            frame1 = "Sprites/Enemies/spooderanim/frame1.png",
            frame2 = "Sprites/Enemies/spooderanim/frame2.png",
            width = 200f,
            height = 150f
        )
    }
}
