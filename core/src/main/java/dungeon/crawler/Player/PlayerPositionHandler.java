package dungeon.crawler.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;

import dungeon.crawler.Sprites.AnimatedSprite;
import dungeon.crawler.Utils.MapUtils;

public class PlayerPositionHandler {
    public float x;
    public float y;
    private boolean blocked;
    
    private PlayerInputHandler playerInputHandler;
    private float movementSpeed;
    private float spriteWidth;
    private float spriteHeight;
  
    private float mapPixelWidth;
    private float mapPixelHeight;

    private TiledMap worldMap;
    private TiledMapTileLayer collisionLayer;

    public PlayerPositionHandler(
        TiledMap worldMap,
        TiledMapTileLayer collisionLayer,
        PlayerInputHandler input,
        float movementSpeed,
        float spriteWidth,
        float spriteHeight,
        float initialX,
        float initialY
    ) {
        this.worldMap = worldMap;
        this.collisionLayer = collisionLayer;
        this.playerInputHandler = input;
        this.blocked = false;
        this.movementSpeed = movementSpeed;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.x = initialX;
        this.y = initialY;
        

    	// Get map dimensions in pixels
        
        // move to util file
    	int mapWidth = worldMap.getProperties().get("width", Integer.class);
    	int mapHeight = worldMap.getProperties().get("height", Integer.class);
    	int tileWidth = worldMap.getProperties().get("tilewidth", Integer.class);
    	int tileHeight = worldMap.getProperties().get("tileheight", Integer.class);

    	this.mapPixelWidth = mapWidth * tileWidth;
    	this.mapPixelHeight = mapHeight * tileHeight;
    }

    public void updateInput() {
    	float newX; 
    	float newY;
        if(playerInputHandler.isMoving){
            // Sprite sprite = playerSprite.getSprite();
            float speed = movementSpeed * Gdx.graphics.getDeltaTime(); // movement speed

            newX = x;
            newY = y;

            // check for collision here
            // adjust for sprite height and length
            float xAdjust;
            float yAdjust;


            if(playerInputHandler.direction == PlayerDirection.LEFT) {
				newX -= speed;
            }
            if(playerInputHandler.direction == PlayerDirection.RIGHT) {
				newX += speed;
                // 3 is an offset to account for sprite width being less than sprite 'tile' width
                // it's a hack and should probably be on the Player class somewhere
                xAdjust = newX + ((spriteWidth / 2) - 3);
            } else {
                xAdjust = newX - ((spriteWidth / 2) - 3);
            }
            yAdjust = newY - (spriteHeight / 2);
            if(playerInputHandler.direction == PlayerDirection.UP) {
				newY += speed;
                yAdjust = newY + (spriteHeight / 2 - 2);

            } 
            if(playerInputHandler.direction == PlayerDirection.DOWN) {
				newY -= speed;
            }

            // 1. Clamp to map edges
            newX = MathUtils.clamp(newX, spriteWidth / 2, mapPixelWidth - spriteWidth / 2);
            newY = MathUtils.clamp(newY, spriteWidth / 2, mapPixelHeight - spriteHeight / 2);
            
            blocked = false;
            Cell tile = MapUtils.getTileCellAtCoord(xAdjust, yAdjust, collisionLayer);

            if(
                tile == null ||
                tile.getTile() == null ||
                tile.getTile().getProperties() == null
            ) {
                blocked = true;
            } else if (tile.getTile().getProperties().containsKey("blocked")){
                blocked = true;
            }

            if(!blocked && playerInputHandler.isMoving) {
                x = newX;
                y = newY;
        		Gdx.app.log("world", String.valueOf(x));

            }
        }
    }
}
