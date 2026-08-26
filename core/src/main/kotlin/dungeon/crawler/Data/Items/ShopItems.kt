package dungeon.crawler.Data.Items

object ShopItemConfig {

    @JvmField
    val registry: Map<Int, List<String>> = mapOf(
        0 to listOf(
            "iron_sword", "wooden_staff", "wooden_club", "wooden_hammer",
            "small_health_potion", "leather_cap", "leather_vest",
            "leather_pants", "bronze_helm", "bronze_chainmail",
            "bronze_chainmail_pants"
        )
    )
}
