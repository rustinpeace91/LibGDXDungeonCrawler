package dungeon.crawler;


import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WorldScreen extends ScreenAdapter {
	private MainGame game;
	private SpriteBatch spriteBatch;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Texture characterTexture;	
	private AnimatedSprite characterSprite;
	
	private float spriteCenterX;
	private float spriteCenterY;
	
	private TiledMapTileLayer collisionLayer;
	
	// map bounderies
	private float mapPixelWidth;
	private float mapPixelHeight;
	
	
	float frameDuration = .15f;
    public WorldScreen(
    	MainGame game,
    	SpriteBatch spriteBatch
    ) {
        this.game = game;
        this.spriteBatch = spriteBatch;
		this.map = new TmxMapLoader().load("Maps/testmap.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);
		
		// set up camera
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		camera.zoom=0.25f;
		camera.update();
		
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
        		16,
        		16
        );
    	this.spriteCenterX = screenCenterX - characterSprite.getSprite().getWidth() / 2f;
    	this.spriteCenterY = screenCenterY - characterSprite.getSprite().getHeight() / 2f;
    	
    	
    	
    	// build collision layers
    	
    	
    	this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("Ground");
    	

	}
    
  private void updateCharacterSprite() {
  	float delta = Gdx.graphics.getDeltaTime();
	Sprite sprite = characterSprite.getSprite();
	

//	spriteCenterX = camera.position.x  - characterSprite.getSprite().getWidth() / 2f;
//	spriteCenterY = camera.position.y  - characterSprite.getSprite().getHeight() / 2f;
//	
//	sprite.setX(spriteCenterX);
//	sprite.setY(spriteCenterY);
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
    }
	@Override
	public void render(float delta) {

		input();
		updateCharacterSprite();
		draw();
		

	}

    private void input() {
    	// character speed 
    	boolean isPlayerBlocked = false;
        float speed = 100 * Gdx.graphics.getDeltaTime(); // movement speed
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
//        Gdx.app.log("state", characterSprite.getState());
        

        Rectangle playerBlock = new Rectangle(
        	newX,
        	newY,
        	sprite.getWidth(),
        	sprite.getHeight()
        );
        // check for collision here
        // adjust for sprite height and length
        float yAdjust = newY - (sprite.getHeight() / 2);
        float xAdjust = newX - ((sprite.getWidth() / 2) - 3);
        if(this.characterSprite.getState() == "walkRight") {
            xAdjust = newX + ((sprite.getWidth() / 2) - 3);
        }
        Cell tile = getTileCellAtCoord(xAdjust, yAdjust, collisionLayer);
        if (tile.getTile().getProperties().containsKey("blocked")){
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
