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

public class WorldScreen extends ScreenAdapter {
	private MainGame game;
	private SpriteBatch spriteBatch;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Texture characterTexture;	
	private AnimatedSprite characterSprite;
	private boolean overWorld;

	
	private TiledMapTileLayer collisionLayer;
	
	// map bounderies
	private float mapPixelWidth;
	private float mapPixelHeight;
	
	private float movementSpeed;
	
	// menu stuff
	private Skin skin;
	private Stage uiStage;
	
	
	float frameDuration = .15f;
    public WorldScreen(
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
    	
    	
    	
    	// set map bounderies
    	
    	// Get map dimensions in pixels
    	int mapWidth = map.getProperties().get("width", Integer.class);
    	int mapHeight = map.getProperties().get("height", Integer.class);
    	int tileWidth = map.getProperties().get("tilewidth", Integer.class);
    	int tileHeight = map.getProperties().get("tileheight", Integer.class);

    	this.mapPixelWidth = mapWidth * tileWidth;
    	this.mapPixelHeight = mapHeight * tileHeight;
    	
    	this.characterSprite = new AnimatedSprite(
        		buildWalkAnim(),
        		"walkDown",
        		screenCenterX,
        		screenCenterY,
        		GameConstants.SPRITE_WIDTH,
        		GameConstants.SPRITE_HEIGHT
        );

    	camera.position.x = startingX;
    	camera.position.y = startingY;
    	
    	// movement speed will be different for towns and overworld

    	this.movementSpeed = GameConstants.PLAYER_SPEED;
       	
    	// build collision layers
    	this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("Ground");

    	this.skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));
		setUpMenu();


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
    	Table menu = new OverworldMenu(this.skin);
        float x = 50;
        float y = Gdx.graphics.getHeight() - menu.getHeight() - 50;
        menu.setPosition(x, y);
        this.uiStage.addActor(menu);
        // --- Configure the InputMultiplexer ---
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        // 6. Tell LibGDX to use the multiplexer for all input events
        Gdx.input.setInputProcessor(multiplexer);
    }
    
  private void updateCharacterSprite() {
  	float delta = Gdx.graphics.getDeltaTime();
	Sprite sprite = characterSprite.getSprite();
	


	sprite.setCenter(camera.position.x, camera.position.y);
	// update animation if walking
	if (characterSprite.walking) {
	    characterSprite.update(delta);
	}


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
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();
    }
	@Override
	public void render(float delta) {

		input();
		updateCharacterSprite();
		draw();
	}

    private void input() {
    	/*
    	 * check for input, project camera to a new x
    	 * set sprite animation
    	 * project camera to a new X,Y coord and check for collisions taking into account player 
    	 * 
    	 */
    	boolean isPlayerBlocked = false;
        float speed = movementSpeed * Gdx.graphics.getDeltaTime(); // movement speed
        Sprite sprite = characterSprite.getSprite();
        float newX = camera.position.x;
        float newY = camera.position.y;

		characterSprite.walking = false;
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newX -= speed;
            characterSprite.setState("walkLeft");
            characterSprite.walking = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newX += speed;
            characterSprite.setState("walkRight");
            characterSprite.walking = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        	newY += speed;
            characterSprite.setState("walkUp");
            characterSprite.walking = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        	newY -= speed;
            characterSprite.setState("walkDown");
            characterSprite.walking = true;
        }
        
        // 1. Clamp to map edges
        newX = MathUtils.clamp(newX, sprite.getWidth() / 2, mapPixelWidth - sprite.getWidth() / 2);
        newY = MathUtils.clamp(newY, sprite.getWidth() / 2, mapPixelHeight - sprite.getHeight() / 2);

        // check for collision here
        // adjust for sprite height and length
        float xAdjust;
        float yAdjust;

        	
        if(this.characterSprite.getState() == "walkUp") {
        	yAdjust = newY + (sprite.getHeight() / 2 - 2);
        } else {
        	yAdjust = newY - (sprite.getHeight() / 2);
        }
        if(this.characterSprite.getState() == "walkRight") {
            // 3 is an offset to account for sprite width being less than sprite 'tile' width
            // it's a hack and should probably be on the Player class somewhere
            xAdjust = newX + ((sprite.getWidth() / 2) - 3);
        } else {
            xAdjust = newX - ((sprite.getWidth() / 2) - 3);
        }
        Cell tile = getTileCellAtCoord(xAdjust, yAdjust, collisionLayer);

        if(
            tile == null ||
            tile.getTile() == null ||
            tile.getTile().getProperties() == null
        ) {
            isPlayerBlocked = true;
        } else if (tile.getTile().getProperties().containsKey("blocked")){
        	isPlayerBlocked = true;
        }

        if(!isPlayerBlocked && characterSprite.walking == true) {
        	camera.position.x = newX;
        	camera.position.y = newY;
        }
        
    }
    // TODO: Make utility
    // Tile based movement?
    public Cell getTileCellAtCoord(float x, float y, TiledMapTileLayer layer) {
        int tileWidth = (int) layer.getTileWidth();
        int tileHeight = (int) layer.getTileHeight();

        int cellX = (int) (x / tileWidth);
        int cellY = (int) (y / tileHeight);
        
        return layer.getCell(cellX, cellY);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        uiStage.dispose();
        skin.dispose();
    }
    
    // TODO Move out of world map
    private Map<String, Animation<TextureRegion>> buildWalkAnim(){
    	Texture fullSheet = new Texture("Sprites/mc_male.png");
    	Map<String, Animation<TextureRegion>> animationMap = new HashMap<String, Animation<TextureRegion>>();
    	
    	animationMap.put("walkDown", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 1, frameDuration));
    	animationMap.put("walkLeft", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 2, frameDuration));
    	animationMap.put("walkRight", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 3, frameDuration));
    	animationMap.put("walkUp", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 4, frameDuration));
    	return animationMap;
    	
    }

}
