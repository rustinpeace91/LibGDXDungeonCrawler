package dungeon.crawler.Data.Spells
import kotlin.random.Random
import dungeon.crawler.GameSystem.Character.Combatant
import dungeon.crawler.GameSystem.Character.Condition
import dungeon.crawler.GameSystem.Combat.Attack
import dungeon.crawler.GameSystem.Combat.AttackDamage
import dungeon.crawler.GameSystem.Combat.Elemental

class Spell (
    val id: SpellNames,
    val name: String,
    val cost: Int,
    val type: SpellType,
    private val castLogic: (caster: Combatant, targets: ArrayList<Combatant>) -> ArrayList<String>
)

object SpellRegistry {
    private val registry = HashMap<SpellNames, Spell>();

    init {
        register(
            Spell(
                SpellNames.FIREBOLT,
                "FireBolt",
                3,
                SpellType.SINGLE_OFFENSE
            ) { caster, targets ->
                val flavorText = ArrayList<String>()
                val target = targets.first();
                // TODO: make this variable,
                val toHit = 2;
                val damageRoll = Random.nextInt(1,10)
                val damage = AttackDamage(
                    toHit,
                    damageRoll,
                    "Casts Firebolt",
                    true,
                    Condition.NOEFFECT,
                    Elemental.FIRE
                )
                flavorText.add("${caster.name} casts firebolt")
                val damageAmount = Attack.handleDamage(
                    caster,
                    target,
                    damage,
                );
                if(
                    damageAmount > 0
                ) {
                    flavorText.add("${caster.name} scorched ${target.name} for ${damageAmount} fire damage!")
                } else {
                    flavorText.add("the attack missed!")
                }
                flavorText
            }
        )
        register(
            Spell(
                id = SpellNames.HEALMINOR,
                name = "Heal Minor Wounds",
                cost = 2,
                type = SpellType.SINGLE_DEFENSE,
            ){ caster, targets ->
                val flavorText = ArrayList<String>()
                val healRoll = Random.nextInt(1,10)
                val target = targets.first()
                // TODO: make this variable,
                flavorText.add("${caster.name} casts firebolt")
                val healed = target.heal(healRoll);
                flavorText.add("{target.name} healed for ${healed} points")
                flavorText
            }
        )
    }

    private fun register(spell: Spell) {
        registry[spell.id] = spell
    }

    fun get(id: SpellNames): Spell? = registry[id]
}

