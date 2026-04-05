package dungeon.crawler.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

// Extend Table directly
public class StandardStatusMenu extends Table {
    private Label messageLabel;

    public StandardStatusMenu(Skin skin) {
        super(skin); // Pass skin to parent Table
        this.setSize(156f, 120f); 

        // Set the background and gray tint
        this.setBackground(skin.getDrawable("default-round"));
        Color semiTransparentGray = new Color(0.2f, 0.2f, 0.2f, 0.8f);
        this.setBackground(skin.newDrawable("default-round", semiTransparentGray));

        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.topLeft);
        // Add the label to 'this' table
        this.add(messageLabel).expand().fill().pad(10f, 10f, 10f, 10f);

    }

    public void setText(String text) {
        messageLabel.setText(text);
    }
}