package dungeon.crawler.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

// Extend Table directly
public class CombatEventScreen extends Table {
    private Label messageLabel;

    public CombatEventScreen(Skin skin) {
        super(skin); // Pass skin to parent Table
        
        // Set the background and gray tint
        this.setBackground(skin.getDrawable("default-round"));
        Color semiTransparentGray = new Color(0.2f, 0.2f, 0.2f, 0.8f);
        this.setBackground(skin.newDrawable("default-round", semiTransparentGray));

        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center); 
        // Add the label to 'this' table
        this.add(messageLabel).width(300f).pad(30f);
        
        this.pack();
    }

    public void setText(String text) {
        messageLabel.setText(text);
    }
}