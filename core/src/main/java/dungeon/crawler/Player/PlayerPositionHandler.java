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
        if(!blocked){
            // Sprite sprite = playerSprite.getSprite();
            float speed = movementSpeed * Gdx.graphics.getDeltaTime(); // movement speed
			Gdx.app.log("world", String.valueOf(playerInputHandler.direction));

            // 1. Clamp to map edges
            newX = MathUtils.clamp(x, spriteWidth / 2, mapPixelWidth - spriteWidth / 2);
            newY = MathUtils.clamp(x, spriteWidth / 2, mapPixelHeight - spriteHeight / 2);

            // check for collision here
            // adjust for sprite height and length
            float xAdjust;
            float yAdjust;


            if(this.playerInputHandler.direction == PlayerDirection.LEFT) {
                yAdjust = newY + (spriteHeight / 2 - 2);
            } else {
                yAdjust = newY - (spriteHeight / 2);
            }
            if(this.playerInputHandler.direction == PlayerDirection.RIGHT) {
                // 3 is an offset to account for sprite width being less than sprite 'tile' width
                // it's a hack and should probably be on the Player class somewhere
                xAdjust = newX + ((spriteWidth / 2) - 3);
            } else {
                xAdjust = newX - ((spriteWidth / 2) - 3);
            }
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
            }
        }
    }
}
