package dungeon.crawler.Data.Items

import dungeon.crawler.GameSystem.Inventory.Item

data class Loot(
    val gold: Int,
    val items: ArrayList<Item>

)
