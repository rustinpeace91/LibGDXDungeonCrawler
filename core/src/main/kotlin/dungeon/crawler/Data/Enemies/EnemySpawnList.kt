package dungeon.crawler.Data.Enemies



object EnemySpawnConfig {

    @JvmField
    val registry: Map<Int, List<String>> = mapOf(
        0 to listOf(
            "rat", "rat", "rat", "rat", "spider"
        ),
        1 to listOf(
            "rat", "rat", "rat", "spider", "spider"
        ),
        2 to listOf(
            "rat", "rat", "spider", "spider", "skeleton"
        )
    )
}
