package dungeon.crawler;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import dungeon.crawler.Menu.CombatMenu;
import dungeon.crawler.Menu.MenuInputHandler;
import dungeon.crawler.Observers.MenuInputObserver;

public class CombatScreen extends ScreenAdapter implements MenuInputObserver {
	private MainGame game;

    private Stage uiStage;
    private Table table;
    private Skin skin;

    private MenuInputHandler menuInputHanlder;

    private SpriteBatch batch;

    private Texture backgroundTexture;
    public CombatScreen(
    	MainGame game
    ) {
    	this.game = game;
        // 1. Setup Stage and Table
        this.uiStage = new Stage(new ScreenViewport());
        this.table = new Table();
        table.setFillParent(true); // Make table the size of the screen
        
        // 2. Load your Skin (ensure path is correct)
        this.skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));

        // 3. Create the Title Label
        Label title = new Label("You just entered the combat zone brother", skin);
        title.setFontScale(2.0f); // Make it big

        this.batch = new SpriteBatch();
        this.backgroundTexture = new Texture(Gdx.files.internal("libgdungeonPOC.png"));
            // 1. Load the PNG
        Texture texture = new Texture(Gdx.files.internal("libgdungeonPOC.png"));

        // 2. Wrap it in an Image actor
        Image imageActor = new Image(texture);

        // 3. Position and add it
        imageActor.setPosition(100, 100);
        uiStage.addActor(imageActor);

        // 4. Add to Table
        table.add(title).expand().center(); 
        uiStage.addActor(table);

	}

    private void draw() {
	}
	@Override
	public void render(float delta) {

	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 2. Draw directly to the screen (Manual Layer)
        batch.begin();
        // Draws the image at x=100, y=100 with its original size
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); 
        batch.end();
        // Update and Draw the Stage
        uiStage.act(delta);
        uiStage.draw();
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
    public void show() {
		setUpMenu();
		//input
		InputMultiplexer multiplexer = setUpInput();
        Gdx.input.setInputProcessor(multiplexer);
    }
    @Override
    public void dispose() {
        // Always clean up!
        uiStage.dispose();
        skin.dispose();
    }

    private void setUpMenu() {
    	this.uiStage = new Stage(new ScreenViewport());
    	CombatMenu menu = new CombatMenu(this.skin);
        float x = Gdx.graphics.getWidth() - menu.getWidth();
        float y = 0;
        menu.setPosition(x, y);
		menu.addScreenChangeObserver(game);
        this.uiStage.addActor(menu);

        this.menuInputHanlder = new MenuInputHandler(
            uiStage,
        	menu
        );
        this.menuInputHanlder.addListener(this);
    }

    public InputMultiplexer setUpInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        // --- Configure the InputMultiplexer ---
        multiplexer.addProcessor(menuInputHanlder);
        // 6. Tell LibGDX to use the multiplexer for all input events
    	return multiplexer;
    }

    // TODO: untangle this from menuInputHanlder
    @Override
    public void onMenuToggled(boolean value) {}

}
