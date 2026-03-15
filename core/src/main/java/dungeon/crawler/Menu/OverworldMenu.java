package dungeon.crawler.Menu;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import dungeon.crawler.GameConstants;
import dungeon.crawler.Observers.ScreenChangeObserver;

public class OverworldMenu extends Table {
	public ArrayList<TextButton> buttonList;
	public ArrayList<ScreenChangeObserver> screenChangeObservers;

	public OverworldMenu(
		Skin skin
	){
		super(skin);
        this.buttonList = new ArrayList<TextButton>();
        Drawable background = skin.getDrawable("default-round"); 
		this.screenChangeObservers = new ArrayList<ScreenChangeObserver>();
		
        
        // 2. Tint it to a semi-transparent gray color (e.g., RGB 0.2, Alpha 0.8)
        // new Color(R, G, B, A)
        Color semiTransparentGray = new Color(0.2f, 0.2f, 0.2f, 0.8f); 
        this.setBackground(skin.newDrawable("default-round", semiTransparentGray));
        this.defaults().pad(10).fillX().minWidth(150); // Set default padding for all cells
	      // Menu buttons
        TextButton inventoryButton = new TextButton("inventory", skin);
        TextButton statusButton = new TextButton("Status", skin);
        
        
        this.buttonList.add(inventoryButton);
        this.buttonList.add(statusButton);

	      // Menu buttons
        TextButton partyButton = new TextButton("Party", skin);
        TextButton searchButton = new TextButton("Options", skin);
		TextButton testCombat = new TextButton("Test Combat", skin);
        
        this.buttonList.add(partyButton);
        this.buttonList.add(searchButton);
		this.buttonList.add(testCombat);
        inventoryButton.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		Gdx.app.log("Button", "Talk Button clicked");
        	}
        });

		addMenuListeners(partyButton, searchButton, testCombat);

		this.add(inventoryButton).row();
		this.add(statusButton).row();
		
		this.add(partyButton).row();
		this.add(searchButton).row();
		this.add(testCombat).row();

		this.pack();
		this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
		Color focusColor = Color.YELLOW;
		Color defaultColor = Color.WHITE;
		for (Actor actor : this.getChildren()) {
			if(actor instanceof TextButton){
 				TextButton button = (TextButton) actor;
				button.addListener(new FocusListener(){
            		@Override
					public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
						TextButton button = (TextButton) actor;
						Label label = button.getLabel();
						if (focused) {
							label.setColor(focusColor);
						} else {
							label.setColor(defaultColor);
						}
					}
				});
			}
		}
	}

	public final void addMenuListeners(
		TextButton partyButton,
		TextButton searchButton,
		TextButton testCombat
	){
		
		testCombat.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Button", "GET READY TO FIGHT clicked");
				notifyScreenChange(GameConstants.GAME_SCREEN.COMBAT);
			}
		});
	}
	

	public void notifyScreenChange(GameConstants.GAME_SCREEN screen){
        for (ScreenChangeObserver observer : screenChangeObservers) {
            observer.onScreenChange(screen);
        }
	}

	public void addScreenChangeObserver(ScreenChangeObserver observer){
		screenChangeObservers.add(observer);
	}



}
