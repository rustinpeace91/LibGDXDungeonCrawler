package dungeon.crawler;


import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import dungeon.crawler.Menu.MenuInputHandler;
import dungeon.crawler.Menu.MenuInputObserver;
import dungeon.crawler.Menu.OverworldMenu;
import dungeon.crawler.Player.PlayerAnimatedSprite;
import dungeon.crawler.Player.PlayerAnimatedSpriteFactory;
import dungeon.crawler.Player.PlayerDirection;
import dungeon.crawler.Player.PlayerInputHandler;
import dungeon.crawler.Player.PlayerPositionHandler;
import dungeon.crawler.Sprites.AnimatedSprite;
import dungeon.crawler.Sprites.AnimationBuilder;

public class WorldScreenRefactor extends ScreenAdapter implements MenuInputObserver{
	private MainGame game;
	private SpriteBatch spriteBatch;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private PlayerAnimatedSprite characterSprite;
	private boolean overWorld;


	private TiledMapTileLayer collisionLayer;

	// movement stuff
	private PlayerInputHandler playerInput;
	private PlayerPositionHandler playerPosition;
	// map bounderies


	private float movementDuration;

	// menu stuff
	private Skin skin;
	private Stage uiStage;
	private MenuInputHandler menuInputHanlder;
	private boolean menuVisible;


    public WorldScreenRefactor(
    	MainGame game,
    	SpriteBatch spriteBatch,
    	float startingX,
    	float startingY,
    	String mapFile,
    	boolean overWorld
    ) {
        this.game = game;
        this.spriteBatch = spriteBatch;
		this.map = new TmxMapLoader().load(mapFile);
		this.renderer = new OrthogonalTiledMapRenderer(map);
		this.overWorld = overWorld;



		setUpCamera();
    	float screenCenterX = camera.viewportWidth / 2f;
    	float screenCenterY = camera.viewportHeight / 2f;


    	camera.position.x = startingX;
    	camera.position.y = startingY;


    	this.movementDuration = GameConstants.TOWN_MOVEMENT_DURATION;

    	// build collision layers
    	this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("Ground");
    	// Player stuff
    	
		this.playerInput = new PlayerInputHandler(PlayerDirection.DOWN);

		this.playerPosition = new PlayerPositionHandler(
			map,
			collisionLayer,
			playerInput,
			movementDuration,
			startingX,
			startingY
		);
		PlayerAnimatedSpriteFactory factory = new PlayerAnimatedSpriteFactory();
		this.characterSprite = factory.createAnimation(
			screenCenterX,
			screenCenterY,
			GameConstants.WALK_ANIMATIONS.get(PlayerDirection.DOWN),
			playerPosition
		);
		playerPosition.addObserver(characterSprite);

		
    	this.skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));
		setUpMenu();

		//input
		InputMultiplexer multiplexer = setUpInput();
        Gdx.input.setInputProcessor(multiplexer);


	}

    private void setUpCamera() {
		// set up camera
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConstants.RESOLUTION_WIDTH, GameConstants.RESOLUTION_HEIGHT);
		camera.zoom=0.25f;
		camera.update();
    }

    private void setUpMenu() {
    	this.uiStage = new Stage(new ScreenViewport());
    	OverworldMenu menu = new OverworldMenu(this.skin);
        float x = 50;
        float y = Gdx.graphics.getHeight() - menu.getHeight() - 50;
        menu.setPosition(x, y);
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

    private void draw() {
    	float delta = Gdx.graphics.getDeltaTime();

	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();
		

	    spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
	    characterSprite.render(spriteBatch);
		spriteBatch.end();
		if(menuVisible) {

			uiStage.act(Gdx.graphics.getDeltaTime());
			uiStage.draw();
		}
    }
	@Override
	public void render(float delta) {

		input(delta);
		draw();
	}

    private void input(float delta) {
    	/*
    	 * check for input, project camera to a new x
    	 * set sprite animation
    	 * project camera to a new X,Y coord and check for collisions taking into account player
    	 *
    	 */

		if(!menuVisible) {
			playerInput.updateInput();
			playerPosition.update(delta);
			camera.position.x = playerPosition.x + 8;
			camera.position.y = playerPosition.y;
			characterSprite.update(
				delta
			);
		}
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        uiStage.dispose();
        skin.dispose();
    }


    public void onMenuToggled(boolean value) {
    	menuVisible = value;
    }

}
