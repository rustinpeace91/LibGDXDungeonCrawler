package dungeon.crawler.Menu;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import dungeon.crawler.GameSystem.Character.PartyCharacter;

public class PartyCharacterStatusMenu extends StandardStatusMenu {
    private PartyCharacter character;
    public PartyCharacterStatusMenu (
        Skin skin,
        PartyCharacter character
    ){
        super(skin);
        this.character = character;
        displayCharacter();


    }

    public void displayCharacter(){
        setText(
            String.format(
                "%s\n HP: %s\n MP: %s\n XP: %s",
                character.name,
                String.valueOf(character.hp),
                String.valueOf(character.mp),
                String.valueOf(character.xp)

            )
        );
    }
}
