package dungeon.crawler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dungeon.crawler.Data.Maps.MapRegistry;
import dungeon.crawler.Data.Maps.ScreenTransitionProperties;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.TestData.EnemyCombatant;
import dungeon.crawler.GameSystem.TestData.EnemyFactory;
import dungeon.crawler.Observers.CombatScreenObserver;
import dungeon.crawler.Observers.ScreenChangeObserver;

public class MainGame extends Game implements ScreenChangeObserver,
    CombatScreenObserver {
    SpriteBatch spriteBatch;
    public GameState gameState;
    @Override
    public void create() {
        gameState = new GameState();
        String mapFile = "Maps/testmap.tmx";
        gameState.updateWorldMap(mapFile);
        spriteBatch = new SpriteBatch();
        setScreen(new WorldScreenRefactor(
            this,
            spriteBatch,
            13f,
            12f,
            mapFile,
            GameConstants.GAME_SCREEN.WALK_TOWN
        ));

        // setScreen(new CombatScreen(this));

        //    setScreen(new MenuScreen());
    }

    @Override
    public void onScreenChange(GameConstants.GAME_SCREEN screen){
        EnemyCombatant enemy = EnemyFactory.generate();
        this.gameState.currentEnemyRoster.put(1, enemy);
        // this.gameState.currentEnemyRoster.put(2, enemy)
        if(screen == GameConstants.GAME_SCREEN.COMBAT){
            CombatScreen combatScreen = new CombatScreen(this);
            setScreen(combatScreen);
        }
    }

    @Override
    public void onMapChange(int ScreenId){
        ScreenTransitionProperties worldScreenData = MapRegistry.WORLD_MAP_DATA.get(ScreenId);
        gameState.updateWorldCoordinates(new Vector2(
            worldScreenData.startingX,
            worldScreenData.startingY
        ));
        gameState.updateWorldMap(worldScreenData.mapFile);
        gameState.updateScreenID(ScreenId);
        setScreen(new WorldScreenRefactor(
            this,
            spriteBatch,
            worldScreenData.startingX,
            worldScreenData.startingY,
            worldScreenData.mapFile,
            worldScreenData.screen
        ));
    };

    @Override
    public void onCombatVictory(){
        ScreenTransitionProperties worldScreenData = MapRegistry.WORLD_MAP_DATA.get(gameState.screenID);

        setScreen(new WorldScreenRefactor(
            this,
            spriteBatch,
            gameState.overWorldCoordinates.x,
            gameState.overWorldCoordinates.y,
            // worldScreenData.startingX,
            // worldScreenData.startingY,
            worldScreenData.mapFile,
            worldScreenData.screen
        ));
    }

    @Override
    public void onCombatLoss(){
        // TODO: set up actual losslogic
        //TODO: BAD 
        gameState.player.hp = 1;
        String mapFile = "Maps/testmap.tmx";
        gameState.updateWorldMap(mapFile);
        spriteBatch = new SpriteBatch();
        setScreen(new WorldScreenRefactor(
            this,
            spriteBatch,
            13f,
            12f,
            mapFile,
            GameConstants.GAME_SCREEN.WALK_TOWN
        ));
    }


}
