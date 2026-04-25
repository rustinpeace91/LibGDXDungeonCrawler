package dungeon.crawler;

import java.util.Map;

import dungeon.crawler.Player.PlayerDirection;
public class GameConstants {
    public enum GAME_SCREEN {
        MENU,
        WALK_OVERWORLD,
        WALK_TOWN,
        COMBAT,
        INN,
        TEST_SCREEN
    };

    // screen
    public static final int RESOLUTION_WIDTH = 960;
    public static final int RESOLUTION_HEIGHT = 720;

    // player
    public static final float TOWN_MOVEMENT_DURATION = 0.15f;
    public static final float OVERWORLD_MOVEMENT_DURATION = 0.25f;
    // public static final float SPRITE_WIDTH = 16;
    // public static final float SPRITE_HEIGHT = 16;
    public static final float SPRITE_WIDTH = 16;
    public static final float SPRITE_HEIGHT = 16;
    public static final float TILE_WIDTH = 16;
    public static final float TILE_HEIGHT = 16;

    public static final float FRAME_DURATION = .15f;



    public static final String TEST_MAP = "Maps/testmap.tmx";
    public static final String CURRENT_SPRITE = "Sprites/top_sheet.png";
    // public static final String MENU_SKIN = "skins/plainjames_modded/plainjames.json";
    public static final String MENU_SKIN = "skins/default/uiskin.json";
    public static final Map<PlayerDirection, String> WALK_ANIMATIONS = Map.of(
        PlayerDirection.UP, "WalkUp",
        PlayerDirection.RIGHT, "WalkRight",
        PlayerDirection.DOWN, "WalkDown",
        PlayerDirection.LEFT, "WalkLeft"
    );

}
