package dungeon.crawler;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CombatScreen extends ScreenAdapter{
	private MainGame game;

    private Stage stage;
    private Table table;
    private Skin skin;
    public CombatScreen(
    	MainGame game
    ) {
    	this.game = game;
        // 1. Setup Stage and Table
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        table.setFillParent(true); // Make table the size of the screen
        
        // 2. Load your Skin (ensure path is correct)
        this.skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));

        // 3. Create the Title Label
        Label title = new Label("You just entered the combat zone brother", skin);
        title.setFontScale(2.0f); // Make it big

        // 4. Add to Table
        table.add(title).expand().center(); 
        stage.addActor(table);
	}

    private void draw() {
	}
	@Override
	public void render(float delta) {

	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Update and Draw the Stage
        stage.act(delta);
        stage.draw();
		// input(delta);
		draw();
	}

    // private void input(float delta) {
    // 	/*
    // 	 * check for input, project camera to a new x
    // 	 * set sprite animatisdfa new X,Y coord and check for collisions taking into account player
    // 	 *
    // 	 */

    // }

    @Override
    public void dispose() {
        // Always clean up!
        stage.dispose();
        skin.dispose();
    }


}
