package dungeon.crawler.Menu.TestMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import dungeon.crawler.GameConstants;
import dungeon.crawler.MainGame;
import dungeon.crawler.Observers.MenuInputObserver;

public class MenuTestScreen extends ScreenAdapter  implements MenuInputObserver {
    private MainGame game;
    private TestShopMenu shopMenu;
    private SpriteBatch batch;
    private Stage uiStage;
    private TestMenuInputHandler menuInputHandler;

    public MenuTestScreen(
        MainGame game
    ){
        this.uiStage = new Stage(new FitViewport(GameConstants.RESOLUTION_WIDTH, GameConstants.RESOLUTION_HEIGHT)); 
        this.game = game;
        this.batch = new SpriteBatch();

    


    }

    @Override
    public void show(){
        Skin skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));
        shopMenu = new TestShopMenu(skin);
        this.uiStage.addActor(shopMenu);
        this.menuInputHandler = new TestMenuInputHandler(
            uiStage,
            shopMenu
        );
        InputMultiplexer multiplexer = setUpInput();
        Gdx.input.setInputProcessor(multiplexer);
        menuInputHandler.setShowMenu(true);


    }
    public InputMultiplexer setUpInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        // --- Configure the InputMultiplexer ---
        this.menuInputHandler.addListener(this);

        multiplexer.addProcessor(menuInputHandler);
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
}
