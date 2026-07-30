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


){
    // Add this inside your Kotlin Spell class
    fun cast(caster: Combatant, targets: ArrayList<Combatant>): ArrayList<String> {
        return castLogic(caster, targets)
    }
}

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
                var toHit = 2;
                val attackRoll = Random.nextInt(1,20)
                val damageRoll = Random.nextInt(1,10)
                toHit = toHit + attackRoll;
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
                flavorText.add("${caster.name} casts Cure minor wounds")
                val healed = target.heal(healRoll);
                flavorText.add("${target.name} healed for ${healed} points")
                flavorText
            }
        )
        register(
            Spell(
                id = SpellNames.RESURRECTION,
                name = "Resurrection",
                cost = 4,
                type = SpellType.RESURRECTION,
            ){ caster, targets ->
                val flavorText = ArrayList<String>()
                flavorText.add("${caster.name} casts Resurrect")
                val target = targets.first();
                target.resurrect();
                flavorText.add("${target.name} has risen from the dead")
                flavorText
            }
        )
        register(
            Spell(
                SpellNames.FIREBALL,
                "FireBall",
                3,
                SpellType.AOE_OFFENSE,
            ) { caster, targets ->
                val flavorText = ArrayList<String>()
                // TODO: make this variable,
                flavorText.add("${caster.name} casts Fireball on all enemies!")
                for(i in targets.indices) {
                    var target = targets[i]
                    var toHit = 2;
                    val attackRoll = Random.nextInt(1,20)
                    val damageRoll = Random.nextInt(1,10)
                    toHit = toHit + attackRoll;
                    val damage = AttackDamage(
                        toHit,
                        damageRoll,
                        "Casts Firebolt",
                        true,
                        Condition.NOEFFECT,
                        Elemental.FIRE
                    )

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
                }

                flavorText
            }
        )
    }

    private fun register(spell: Spell) {
        registry[spell.id] = spell
    }

    fun get(id: SpellNames): Spell? = registry[id]
}

