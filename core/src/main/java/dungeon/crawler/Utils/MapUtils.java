package dungeon.crawler.Utils;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class MapUtils {
    public static Cell getTileCellAtCoord(float x, float y, TiledMapTileLayer layer) {
        int tileWidth = (int) layer.getTileWidth();
        int tileHeight = (int) layer.getTileHeight();

        int cellX = (int) (x / tileWidth);
        int cellY = (int) (y / tileHeight);

        return layer.getCell(cellX, cellY);
    }

}
