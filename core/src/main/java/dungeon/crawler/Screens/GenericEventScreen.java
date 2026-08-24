package dungeon.crawler.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import dungeon.crawler.Controls.GameInputHandler;
import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.MainGame;
import dungeon.crawler.Menu.CombatEventMenu;
import dungeon.crawler.Menu.InputHandlers.MenuInputHandler;
import dungeon.crawler.Menu.Shop.ShopMainMenu;
import dungeon.crawler.Menu.TestMenus.TestShopMenu;
import dungeon.crawler.Observers.EventScreenObserver;
import dungeon.crawler.Observers.MenuInputObserver;

import java.util.ArrayList;

public class GenericEventScreen extends ScreenAdapter  implements MenuInputObserver, EventScreenObserver {
    private final ArrayList<String> messages;
    private final Vector2 dimensions;
    private MainGame game;
    private SpriteBatch batch;
    private Stage uiStage;
    private MenuInputHandler menuInputHandler;

    private Texture backgroundTexture;
    private ArrayList<Item> inventory;
    private Skin skin;
    private GameInputHandler gameInputHandler;
    private CombatEventMenu eventScreen;

    public GenericEventScreen(
        MainGame game,
        ArrayList<String> messages,
        String background_image,
        Vector2 dimensions
    ){
        this.uiStage = new Stage(new FitViewport(GameConstants.RESOLUTION_WIDTH, GameConstants.RESOLUTION_HEIGHT));
        this.game = game;
        this.batch = new SpriteBatch();
        this.messages = messages;
        this.dimensions = dimensions;
        skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));

        this.backgroundTexture = new Texture(Gdx.files.internal(background_image));
        // 1. Load the PNG
        Texture texture = new Texture(Gdx.files.internal(background_image));

        // 2. Wrap it in an Image actor
        Image imageActor = new Image(texture);




        imageActor.setScaling(Scaling.stretch); // This forces it to stretch to the actor's bounds

        imageActor.setFillParent(true);

        uiStage.addActor(imageActor);
    }

    @Override
    public void show(){
        gameInputHandler = new GameInputHandler();
        game.getControllerAdapter().attach(gameInputHandler);

        eventScreen = new CombatEventMenu(this.skin, gameInputHandler);
        gameInputHandler.addListener(eventScreen);
        eventScreen.setPosition(
            (uiStage.getWidth() - eventScreen.getWidth()) / 2f,
            10f
        );
        eventScreen.setSize(dimensions.x, dimensions.y);
        this.uiStage.addActor(eventScreen);
        eventScreen.addListener(this);


//        this.menuInputHandler = new MenuInputHandler(
//            uiStage,
//            gameInputHandler
//        );
        InputMultiplexer multiplexer = setUpInput();
        Gdx.input.setInputProcessor(multiplexer);
        String[] array = messages.toArray(new String[0]);
        eventScreen.addMessages(array);
        uiStage.setKeyboardFocus(eventScreen);

    }
    public InputMultiplexer setUpInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        // --- Configure the InputMultiplexer ---
//        this.menuInputHandler.addListener(this);

        multiplexer.addProcessor(gameInputHandler);
        multiplexer.addProcessor(uiStage);
        // 6. Tell LibGDX to use the multiplexer for all input events
        return multiplexer;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 2. Draw directly to the screen (Manual Layer)
        batch.begin();
        // Draws the image at x=100, y=100 with its original size
        // batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();


        // Update and Draw the Stage
        uiStage.act(delta);
        uiStage.draw();
        // input(delta);
    }

    @Override
    public void onMenuToggled(boolean menuVisible){};


    @Override
    public void dispose() {
        skin.dispose();
        uiStage.dispose();
        this.backgroundTexture .dispose();
    }

    @Override
    public void hide(){
        game.getControllerAdapter().detach();

    }

    public void exitShop(){
        game.onScreenChange(GameConstants.GAME_SCREEN.WALK_TOWN);
    }

    @Override
    public void onFirstMessageAdded() {

    }

    @Override
    public void onLastMessageRead() {
        game.backToOverworld();
    }
}
