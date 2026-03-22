package dungeon.crawler;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import dungeon.crawler.Menu.CombatEventScreen;
import dungeon.crawler.Menu.CombatMenu;
import dungeon.crawler.Menu.CombatPartyOrderScreen;
import dungeon.crawler.Menu.CurrentFighterStatusScreen;
import dungeon.crawler.Menu.MenuInputHandler;
import dungeon.crawler.Observers.MenuInputObserver;

public class CombatScreen extends ScreenAdapter implements MenuInputObserver {
	private MainGame game;

    private Stage uiStage;
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

        // 2. Load your Skin (ensure path is correct)
        this.skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));


        this.batch = new SpriteBatch();
        this.backgroundTexture = new Texture(Gdx.files.internal("libgdungeonPOC.png"));
            // 1. Load the PNG
        Texture texture = new Texture(Gdx.files.internal("libgdungeonPOC.png"));

        // 2. Wrap it in an Image actor
        Image imageActor = new Image(texture);



        // 3. Position and add it
        // imageActor.setPosition(100, 100);
        imageActor.setScaling(Scaling.stretch); // This forces it to stretch to the actor's bounds

        // 2. Tell it to fill the entire stage
        imageActor.setFillParent(true); 

        uiStage.addActor(imageActor);


        Texture ratTexture = new Texture(Gdx.files.internal("Sprites/Enemies/testrat.png"));
        Image testRatImage = new Image(ratTexture );
        testRatImage.setPosition(250, 100);
        testRatImage.setScale(0.5f);
        uiStage.addActor(testRatImage);
        // 4. Add to Table
        // uiStage.addActor(table);

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

    private void setUpMenu() {
    	// this.uiStage = new Stage(new ScreenViewport());
    	CombatMenu menu = new CombatMenu(this.skin);



		menu.addScreenChangeObserver(game);
        // menu.setOrigin(Align.right); 
        // menu.setOrigin(Align.bottom);
        
        // consider making these mwnua  properties
        float x = Gdx.graphics.getWidth() - menu.getWidth();
        float y = 0;
        menu.setPosition(x, y);
        this.uiStage.addActor(menu);

        CombatEventScreen eventScreen = new CombatEventScreen(this.skin);
        eventScreen.setText("The Golden Elf lifts up thy sword and strikes at thee" );
        eventScreen.setPosition(
            (uiStage.getWidth() - eventScreen.getWidth()) / 2f, 
            10f
        );
        this.uiStage.addActor(eventScreen);

        CombatPartyOrderScreen partyScreen = new CombatPartyOrderScreen(skin);

        partyScreen.setText("FGHT \n HP: 20 \n MP: 0 \n\nWIZR \n HP: 10 \n MP: 10 ");
        partyScreen.setPosition(0, 0);
        this.uiStage.addActor(partyScreen);

        float statusScreenHeight = Math.abs(menu.getHeight() - uiStage.getHeight());
        CurrentFighterStatusScreen currentFighterScreen = new CurrentFighterStatusScreen(skin, statusScreenHeight);
        currentFighterScreen.setText("Edmund\n-poisoned\n-blighted\n-famished\n-in cover\n-blinded");
        currentFighterScreen.setPosition(
            (uiStage.getWidth() - currentFighterScreen.getWidth()),
            menu.getHeight()
        );
        this.uiStage.addActor(currentFighterScreen);

        this.menuInputHanlder = new MenuInputHandler(
            uiStage,
        	menu
        );
        this.menuInputHanlder.addListener(this);
        this.menuInputHanlder.setShowMenu(true);
    }

    public InputMultiplexer setUpInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        // --- Configure the InputMultiplexer ---
        multiplexer.addProcessor(menuInputHanlder);
        // 6. Tell LibGDX to use the multiplexer for all input events
    	return multiplexer;
    }

    // @Override
    // public void resize(int width, int height) {
    //     // This updates the Stage's internal coordinate system to match the window
    //     uiStage.getViewport().update(width, height, true);
    // }

    // TODO: untangle this from menuInputHanlder
    @Override
    public void onMenuToggled(boolean value) {}

}
