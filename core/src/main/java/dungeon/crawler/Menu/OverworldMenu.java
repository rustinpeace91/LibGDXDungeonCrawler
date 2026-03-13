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

public class OverworldMenu extends Table {
	public ArrayList<TextButton> buttonList;
	
	public OverworldMenu(
		Skin skin
	){
		super(skin);
        this.buttonList = new ArrayList<TextButton>();
        Drawable background = skin.getDrawable("default-round"); 
        
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
        
        this.buttonList.add(partyButton);
        this.buttonList.add(searchButton);
        inventoryButton.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		Gdx.app.log("Button", "Talk Button clicked");
        	}
        });

		this.add(inventoryButton).row();
		this.add(statusButton).row();
		
		this.add(partyButton).row();
		this.add(searchButton).row();

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
	





}
