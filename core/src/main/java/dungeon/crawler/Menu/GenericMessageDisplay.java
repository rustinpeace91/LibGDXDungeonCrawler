package dungeon.crawler.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import dungeon.crawler.GameConstants;

import java.util.ArrayList;
import java.util.LinkedList;

// Removed GameInputObserver implementation
public class GenericMessageDisplay extends Table {

    private Label messageLabel;


    public GenericMessageDisplay(Skin skin) {
        super(skin);

        // Background setup
        this.setBackground(skin.getDrawable(GameConstants.SKIN_BACKGROUND_DEFAULT));
        Color semiTransparentGray = new Color(0.2f, 0.2f, 0.2f, 0.8f);
        this.setBackground(skin.newDrawable(GameConstants.SKIN_BACKGROUND_DEFAULT, semiTransparentGray));

        // Label setup
        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);

        // Layout structure
        this.add(messageLabel).width(300f).pad(50f);



        // Sizing
        this.pack();
        this.setSize(400f, 100f);

        // Completely removed GameInputHandler reference and listeners
    }



    public void setText(String text) {
        messageLabel.setText(text);
    }


}
