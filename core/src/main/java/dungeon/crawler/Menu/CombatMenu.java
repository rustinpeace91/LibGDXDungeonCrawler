package dungeon.crawler.Menu;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CombatMenu extends BaseMenu {

	public CombatMenu (
		Skin skin
	) {
		super(skin);
		// TODO: Make into a for loop?
        TextButton fightButton = new TextButton("Fight", skin);
        TextButton magicButton = new TextButton("Magic", skin);
        TextButton defendButton = new TextButton("Defend", skin);
        TextButton inventoryButton = new TextButton("Inventory", skin);
		TextButton runButton = new TextButton("Run", skin);

        this.buttonList.add(fightButton);
        this.buttonList.add(magicButton);
        this.buttonList.add(defendButton);
        this.buttonList.add(inventoryButton);
		this.buttonList.add(runButton);


		// addMenuListeners(defendButton, inventoryButton, runButton);

		this.add(fightButton).row();
		this.add(magicButton).row();
		
		this.add(defendButton).row();
		this.add(inventoryButton).row();
		this.add(runButton).row();

        this.defaults().pad(2); 

		this.pack();
		this.addFocusListeners();
	}

	// public final void addMenuListeners(
	// 	TextButton defendButton,
	// 	TextButton inventoryButton,
	// 	TextButton runButton
	// ){
		
	// 	runButton.addListener(new ChangeListener(){
	// 		@Override
	// 		public void changed(ChangeEvent event, Actor actor) {
	// 			Gdx.app.log("Button", "GET READY TO FIGHT clicked");
	// 			notifyScreenChange(GameConstants.GAME_SCREEN.COMBAT);
	// 		}
	// 	});
	// }
	

	// public void notifyScreenChange(GameConstants.GAME_SCREEN screen){
    //     for (ScreenChangeObserver observer : screenChangeObservers) {
    //         observer.onScreenChange(screen);
    //     }
	// }

	// public void addScreenChangeObserver(ScreenChangeObserver observer){
	// 	screenChangeObservers.add(observer);
	// }



}
