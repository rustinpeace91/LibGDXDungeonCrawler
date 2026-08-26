package dungeon.crawler.Data.Events
import com.badlogic.gdx.math.Vector2
import dungeon.crawler.GameConstants


class Event (
    val id: String,
    val title: String,
    val backgroundImage: String,
    val text: Array<String>,
    val finalEvent: Boolean,
    val nextId: String,
    val messageSize: Vector2


){

}

object EventRegistry {
    private val registry = HashMap<String, Event>();

    init {
        register(
            Event(
                id = "intro",
                title = "Welcome Brave Hero",
                backgroundImage = GameConstants.INTRO_SCREEN_1,
                text = arrayOf(
                    "Greetings my loyal servent of arms. I hath asked you to gather 3 of your bravest " +
                    "companions and gathered you here today to entrust you with a noble and harrowing quest. You, " +
                    "the captain of my guard unit, hath proven thineself as a mighty and noble warrior. Skilled both " +
                    "in the art of sword and sorcery\n",
                    ),
                finalEvent = false,
                nextId= "intro2",
                messageSize = GameConstants.EVENT_MENU_MEDIUM_DIALOGUE
            )
        )

        register(
            Event(
                id = "intro2",
                title = "Welcome Brave Hero",
                backgroundImage = GameConstants.INTRO_SCREEN_2,
                text = arrayOf(
                    "A migthy red bald headed dragon has maken a home atop Mount Kilead. From there it hath scorched " +
                        "many a caravan, and continues to wreak havoc on the villages below. As the steward of this realm. " +
                        "I cannot abide by this. \n"
                ),
                finalEvent = false,
                nextId= "intro3",
                messageSize = GameConstants.EVENT_MENU_MEDIUM_DIALOGUE
            )
        )
        register(
            Event(
                id = "intro3",
                title = "Welcome Brave Hero",
                backgroundImage = GameConstants.INTRO_SCREEN_1,
                text = arrayOf(
                    "I must send you into the wilderness.  You will face many challenges there.  The prescence of " +
                        "the dragon has nullified all attempts at pest control on the main roads. They are now filled with " +
                        "fierce monsters who make trade impossible. You will be arriving at the town of portentia. " +
                        "I advise that you stock up on equipment at the local shops first.  Now go forth. " +
                        "Lift up thy sword and kick the foul beast into the firey pits of hell!\n",
                ),
                finalEvent = true,
                nextId= "",
                messageSize = GameConstants.EVENT_MENU_MEDIUM_DIALOGUE
            )
        )
        register(
            Event(
                id = "steal_fail_1",
                title = "You got yourself caught",
                backgroundImage = GameConstants.STEAL_FAIL_SCREEN_1,
                text = arrayOf(
                    "The Thief attempts to break the window to go in the back of the store",
                ),
                finalEvent = false,
                nextId= "steal_fail_2",
                messageSize = GameConstants.EVENT_MENU_SMALL_DIALOGUE
            )
        )
        register(
            Event(
                id = "steal_fail_2",
                title = "You got yourself caught",
                backgroundImage = GameConstants.STEAL_FAIL_SCREEN_2,
                text = arrayOf(
                    "However, a nearby horse alerts the guards to all of the noise",
                    "The guards stomp onto the scene"
                    ),
                finalEvent = false,
                nextId= "steal_fail_3",
                messageSize = GameConstants.EVENT_MENU_SMALL_DIALOGUE
            )
        )
        register(
            Event(
                id = "steal_fail_3",
                title = "You got yourself caught",
                backgroundImage = GameConstants.STEAL_FAIL_SCREEN_3,
                text = arrayOf(
                    "STOP! You have violated the sanctity of the King's commerce!",
                    "You now must PAY WITH YOUR LIFE!",
                    "Before you can react, axes behead all the thieves in your party"
                ),
                finalEvent = true,
                nextId= "",
                messageSize = GameConstants.EVENT_MENU_SMALL_DIALOGUE
            )
        )
        register(
            Event(
                id = "innsleep",
                title = "Sleep",
                backgroundImage = GameConstants.SLEEP_SCREEN,
                text = arrayOf(
                    "You get a full nights sleep and wake up refreshed"
                ),
                finalEvent = true,
                nextId= "",
                messageSize = GameConstants.EVENT_MENU_SMALL_DIALOGUE
            )
        )

        // end
        register(
            Event(
                id = "ending",
                title = "YOU DID IT",
                backgroundImage = GameConstants.INTRO_SCREEN_1,
                text = arrayOf(
                    "You have freed this great land from the wrath of the mighty dragon " +
                    "We thank you for your valiance in the face of danger! Villages will no longer cower " +
                    "under the threat of flames.  You will be greatly rewarded",
                    "A large plot of land will be comissioned for you to lord over " +
                    "40 Oxen shall graze at your farmlands and your fields shall prosper!"
                ),
                finalEvent = true,
                nextId= "",
                messageSize = GameConstants.EVENT_MENU_MEDIUM_DIALOGUE
            )
        )
    }

    private fun register(event: Event) {
        registry[event.id] = event
    }

    fun get(id: String): Event? = registry[id]
}

