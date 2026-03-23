package dungeon.crawler;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dungeon.crawler.Data.Maps.MapRegistry;
import dungeon.crawler.Data.Maps.ScreenTransitionProperties;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.GameSystem.TestData.EnemyCombatant;
import dungeon.crawler.GameSystem.TestData.EnemyFactory;
import dungeon.crawler.Observers.ScreenChangeObserver;

public class MainGame extends Game implements ScreenChangeObserver {
SpriteBatch spriteBatch;
GameState gameState;


    @Override
    public void create() {
        gameState = new GameState();
        spriteBatch = new SpriteBatch();
    setScreen(new WorldScreenRefactor(
    this,
    spriteBatch,
            13f,
            12f,
    "Maps/testmap.tmx",
    GameConstants.GAME_SCREEN.WALK_TOWN
    ));

// setScreen(new CombatScreen(this));

//    setScreen(new MenuScreen());
    }

@Override
public void onScreenChange(GameConstants.GAME_SCREEN screen){
    EnemyCombatant enemy = EnemyFactory.generate();
    this.gameState.currentEnemyRoster = new ArrayList<EnemyCombatant>();
    this.gameState.currentEnemyRoster.add(enemy);
    if(screen == GameConstants.GAME_SCREEN.COMBAT){
        setScreen(new CombatScreen(this));
    }

}

@Override
public void onMapChange(int ScreenId){
ScreenTransitionProperties worldScreenData = MapRegistry.WORLD_MAP_DATA.get(ScreenId);

    setScreen(new WorldScreenRefactor(
    this,
    spriteBatch,
    worldScreenData.startingX,
    worldScreenData.startingY,
    worldScreenData.mapFile,
    worldScreenData.screen
    ));

};

}
