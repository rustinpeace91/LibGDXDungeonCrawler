package dungeon.crawler.Menu.Overworld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Menu.BaseLinearMenu;
import dungeon.crawler.Utils.StringUtils;

public class StatusSelectionMenu extends BaseLinearMenu {
    private GameState gameState;
    private PartyCharacterStatusMenu partyStatusMenu;

	public StatusSelectionMenu(
        Skin skin,
        GameState gameState
    ){
        super(skin);
        this.gameState = gameState;
        this.partyStatusMenu = new PartyCharacterStatusMenu(
            skin,
            gameState.player
        );

        // shut up linter
        this.subStatusMenu = partyStatusMenu;
    }



    @Override
    protected void setStage(Stage stage){
        // loop through party members, store name and object for status focus
        super.setStage(stage);
        if(gameState.party != null){
            gameState.party.forEach(
                (key, character) -> {
                    addButton(
                        character.name,
                        new ChangeListener (){
                            @Override
                            public void changed(ChangeEvent event, Actor actor){
                                // nothing for now. This is for hovering
                            }
                        },
                        character
                    );
                }
            );
        }

        if(parentMenu != null){
            this.setPosition(this.parentMenu.getOriginX() + 250, Gdx.graphics.getHeight() - this.getHeight() - 210);
        }

        this.pack();
        
        if (stage != null) {

            float x = Gdx.graphics.getWidth() - getWidth() - 200;
            float y = Gdx.graphics.getHeight() - getHeight() - 400;
            subStatusMenu.setPosition(
                x, y
            );
            stage.addActor(subStatusMenu);

            setMenuVisibility(true); 
        }

    }



    @Override
    public void addFocusListeners(){
        super.addFocusListeners();
        for (Actor actor : this.getChildren()) {
            if(actor instanceof TextButton){
                TextButton button = (TextButton) actor;
                button.addListener(new FocusListener(){
                @Override
                public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                    TextButton button = (TextButton) actor;
                    PartyCharacter character = (PartyCharacter)button.getUserObject();
                    if (focused) {
                        if(button.getUserObject() instanceof PartyCharacter){
                            partyStatusMenu.showCharacter(character);
                        } else {
                            partyStatusMenu.setText(StringUtils.format("This is the status text for: \n %s", button.getText()));

                        }
                        // subStatusMenu.setVisible(true);
                    } else {
                    }
                }
                });
            }
        }
    }
}
