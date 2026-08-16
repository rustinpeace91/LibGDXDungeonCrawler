package dungeon.crawler.Data.Descriptions

class CharClassDescriptions {

    companion object {
        val descriptions: Map<String, String> = mapOf(
            "Fighter" to """
                A fierce warrior proficient in all weapons
            """.trimIndent(),
            "Wizard" to """
                A wise scholar of destruction proficient in spell casting

                Weapons available:
                    - Staff
                Armor Available
                    - Basic
            """.trimIndent(),
            "Thief" to """
                A shady mysterious stranger with high dexterity

                Weapons Available:
                    - Shortsword, Staff, Sling, Crossbow, Shortbow
                Armor Available:
                    - Basic, Light
                Notes:
                    - Can steal from stores
            """.trimIndent()
        )

        fun getDescriptionFor(className: String): String {
            return descriptions[className] ?: "An unknown wanderer of the deep dungeons."
        }
    }
}
