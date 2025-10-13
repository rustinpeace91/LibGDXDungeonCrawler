package dungeon.crawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class OverworldMenu extends Table {
	public TextButton[][] buttonList = new TextButton[10][3];
	
	public OverworldMenu(
		Skin skin
	){
		super(skin);
        Drawable background = skin.getDrawable("default-round"); 
        
        // 2. Tint it to a semi-transparent gray color (e.g., RGB 0.2, Alpha 0.8)
        // new Color(R, G, B, A)
        Color semiTransparentGray = new Color(0.2f, 0.2f, 0.2f, 0.8f); 
        this.setBackground(skin.newDrawable("default-round", semiTransparentGray));
        this.defaults().pad(10).fillX().minWidth(150); // Set default padding for all cells
	      // Menu buttons
        TextButton inventoryButton = new TextButton("inventory", skin);
        TextButton statusButton = new TextButton("Status", skin);
        
        
        this.buttonList[0][0] = inventoryButton;
        this.buttonList[0][1] = statusButton;

	      // Menu buttons
        TextButton partyButton = new TextButton("Party", skin);
        TextButton searchButton = new TextButton("Search", skin);
        
        this.buttonList[1][0] = inventoryButton;
        this.buttonList[1][1] = statusButton;
        inventoryButton.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		Gdx.app.log("Button", "Talk Button clicked");
        	}
        });

		this.add(inventoryButton);
		this.add(statusButton).row();
		
		this.add(partyButton);
		this.add(searchButton).row();

		this.pack();
		this.setPosition(50, Gdx.graphics.getHeight() - this.getHeight() - 50);
	}
	



}
