package dungeon.crawler.Observers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import dungeon.crawler.Player.PlayerDirection;

public interface PlayerPositionObserver {
	void onDirectionChange(PlayerDirection newDirection);
	void onEnteredNewTile(Cell tileCell);
	void onTransition(int screenID);
}
