package dungeon.crawler.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import dungeon.crawler.GameConstants;

// Extend Table directly
public class CombatPartyOrderScreen extends Table {
    private Label messageLabel;

    public CombatPartyOrderScreen(Skin skin) {
        super(skin); // Pass skin to parent Table
        this.setSize(128f, 480f); 

        // Set the background and gray tint
        this.setBackground(skin.getDrawable(GameConstants.SKIN_BACKGROUND_DEFAULT));
        Color semiTransparentGray = new Color(0.2f, 0.2f, 0.2f, 0.8f);
        this.setBackground(skin.newDrawable(GameConstants.SKIN_BACKGROUND_DEFAULT, semiTransparentGray));

        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.topLeft);
        // Add the label to 'this' table
        this.add(messageLabel).expand().fill().pad(20f, 20f, 20f, 20f);

    }

    public void setText(String text) {
        messageLabel.setText(text);
    }
}