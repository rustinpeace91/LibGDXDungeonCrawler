package dungeon.crawler.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Combat.CombatLogic;
import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.CombatPhase;
import dungeon.crawler.MainGame;
import dungeon.crawler.Menu.CombatEventScreen;
import dungeon.crawler.Menu.CombatMenu;
import dungeon.crawler.Menu.CombatPartyOrderScreen;
import dungeon.crawler.Menu.CurrentFighterStatusScreen;
import dungeon.crawler.Menu.InputHandlers.MenuInputHandler;
import dungeon.crawler.Observers.ActionSelectObserver;
import dungeon.crawler.Observers.CombatLogicObserver;
import dungeon.crawler.Observers.CombatScreenObserver;
import dungeon.crawler.Observers.EventScreenObserver;
import dungeon.crawler.Observers.MenuInputObserver;
import dungeon.crawler.Utils.StringUtils;

public class CombatScreen extends ScreenAdapter
    implements MenuInputObserver,
    ActionSelectObserver,
    EventScreenObserver,
    CombatLogicObserver {
    private MainGame game;

    private Stage uiStage;
    private Skin skin;

    private MenuInputHandler menuInputHanlder;

    private SpriteBatch batch;
    private CombatLogic logicHandler;

    private CombatEventScreen  eventScreen;
    private CombatMenu combatMenu;
    private CombatPartyOrderScreen partyScreen;

    private Texture backgroundTexture;
    private CombatScreenObserver combatScreenObserver;

    private float worldWidth;
    private float worldHeight;

    private Image testRatImage;

    public CombatScreen(
        MainGame game
    ) {
        this.game = game;
        this.addListener(game);

        // 1. Setup Stage and Table
        // this.uiStage = new Stage(new ScreenViewport());
        this.uiStage = new Stage(new FitViewport(GameConstants.RESOLUTION_WIDTH, GameConstants.RESOLUTION_HEIGHT));
        // 2. Load your Skin (ensure path is correct)
        this.skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));


        this.batch = new SpriteBatch();
        this.backgroundTexture = new Texture(Gdx.files.internal("libgfieldPOC.png"));
            // 1. Load the PNG
        Texture texture = new Texture(Gdx.files.internal("libgfieldPOC.png"));

        // 2. Wrap it in an Image actor
        Image imageActor = new Image(texture);



        // 3. Position and add it
        // imageActor.setPosition(100, 100);
        imageActor.setScaling(Scaling.stretch); // This forces it to stretch to the actor's bounds

        // 2. Tell it to fill the entire stage
        imageActor.setFillParent(true);

        uiStage.addActor(imageActor);


        Texture ratTexture = new Texture(Gdx.files.internal("Sprites/Enemies/testrat2.png"));
        testRatImage = new Image(ratTexture );


        // Position the rat relative to the screen size
        // Example: 20% from the left, 15% from the bottom
        testRatImage.setPosition(worldWidth * 0.45f, worldHeight * 0.15f);
        testRatImage.setScale(0.90f);
        uiStage.addActor(testRatImage);

        // 4. Add to Table
        // uiStage.addActor(table);


    }

    private void draw() {
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 2. Draw directly to the screen (Manual Layer)
        batch.begin();
        // Draws the image at x=100, y=100 with its original size
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        advanceCombat();

        // Update and Draw the Stage
        uiStage.act(delta);
        uiStage.draw();
        // input(delta);
        draw();
    }

    // private void input(float delta) {
    // /*
    //  * check for input, project camera to a new x
    //  * set sprite animatisdfa new X,Y coord and check for collisions taking into account player
    //  *
    //  */

    // }

    @Override
    public void show() {
        setUpMenu();
        //input
        InputMultiplexer multiplexer = setUpInput();
        Gdx.input.setInputProcessor(multiplexer);



        // phase = CombatPhase.INTRO;
        String enemyName = game.gameState.currentEnemyRoster.get(1).name;
        String[] introText = new String[] {
            StringUtils.format("A %s pops up!", enemyName),
            "prepare to fight"
        };
        eventScreen.addMessages(introText);
        this.uiStage.setKeyboardFocus(eventScreen);
        this.logicHandler = new CombatLogic(eventScreen, game);
        this.logicHandler.addListener(this);
        this.logicHandler.advanceState(CombatPhase.INTRO);
    }

    private void setUpMenu() {
        // this.uiStage = new Stage(new ScreenViewport());

        combatMenu = new CombatMenu(
            this.skin,
            this.game.gameState
        );

        combatMenu.addScreenChangeObserver(game);
        combatMenu.addActionSelectObserver(this);
        // menu.setOrigin(Align.right);
        // menu.setOrigin(Align.bottom);

        // consider making these mwnua  properties
        float x = Gdx.graphics.getWidth() - combatMenu.getWidth();
        float y = 0;
        combatMenu.setPosition(x, y);
        this.uiStage.addActor(combatMenu);

        eventScreen = new CombatEventScreen(this.skin);

        eventScreen.setPosition(
            (uiStage.getWidth() - eventScreen.getWidth()) / 2f,
            10f
        );
        this.uiStage.addActor(eventScreen);
        eventScreen.addListener(this);

        partyScreen = new CombatPartyOrderScreen(skin);

        partyScreen.setPosition(0, 0);
        this.uiStage.addActor(partyScreen);

        float statusScreenHeight = Math.abs(combatMenu.getHeight() - uiStage.getHeight());
        CurrentFighterStatusScreen currentFighterScreen = new CurrentFighterStatusScreen(skin, statusScreenHeight);
        currentFighterScreen.setText(
            StringUtils.format("%s\n-various\n-ailments\n-will\n-go\n-here", this.game.gameState.player.name)
        );
        currentFighterScreen.setPosition(
            (uiStage.getWidth() - currentFighterScreen.getWidth()),
            combatMenu.getHeight()
        );
        this.uiStage.addActor(currentFighterScreen);

        this.menuInputHanlder = new MenuInputHandler(
            uiStage,
            combatMenu
        );
        this.menuInputHanlder.addListener(this);
        combatMenu.setActive(false);
        menuInputHanlder.setHandlerDisabled(true);
        updatePartyScreen();

    }

    public InputMultiplexer setUpInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        // --- Configure the InputMultiplexer ---
        this.menuInputHanlder.addListener(this);
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(menuInputHanlder);
        // 6. Tell LibGDX to use the multiplexer for all input events
        return multiplexer;
    }

    public void advanceCombat(){

        logicHandler.advanceCombat();
    }

    public void updatePartyScreen(){
        // TODO: move this to PartyScreen class
        StringBuilder sb = new StringBuilder();
        game.gameState.party.forEach(
            (key, character) -> {
                sb.append(
                    StringUtils.format(
                        "%s \n HP: %s \n MP: %s \n\n",
                        character.name,
                        String.valueOf(
                            character.hp
                        ),
                        String.valueOf(character.mp)
                    )
                );
            }
        );
        partyScreen.setText(sb.toString());
    }

    @Override
    public void resize(int width, int height) {
        // 'true' centers the camera, which is usually best for menus
        uiStage.getViewport().update(width, height, false);

        this.worldWidth = uiStage.getViewport().getWorldWidth();
        this.worldHeight = uiStage.getViewport().getWorldHeight();
        testRatImage.setPosition(worldWidth * 0.45f, worldHeight * 0.15f);

        combatMenu.setPosition(worldWidth - combatMenu.getWidth(), 0);
        partyScreen.setPosition(0, 0);
        partyScreen.setSize(worldWidth * 0.15f, worldHeight);
        partyScreen.invalidate();
    }

    // TODO: untangle this from menuInputHanlder
    @Override
    public void onMenuToggled(boolean value) {}

    @Override
    public void onActionMenuFocus(){
        uiStage.setKeyboardFocus(combatMenu);
        combatMenu.resetMenu();
        menuInputHanlder.setHandlerDisabled(false);
    }

    @Override
    public void onActionMenuReset(){
        uiStage.setKeyboardFocus(combatMenu);
        combatMenu.initializeMenu();
        menuInputHanlder.setHandlerDisabled(false);
    }

    @Override
    public void onActionSelect(int CombatantID, CombatActionState actionState){
        logicHandler.addAction(CombatantID, actionState, 1);
    }

    @Override
    public void onPlayerActionSelectComplete(){
        logicHandler.playerActionSelectComplete();
    }

    @Override
    public void onActionSelectComplete(){
        combatMenu.setActive(false);
        uiStage.setKeyboardFocus(eventScreen);
    }

    @Override
    public void onActionComplete(){
        updatePartyScreen();
    }

    @Override
    public void onFirstMessageAdded(){
        combatMenu.setReadingMessages(true);
        // uiStage.setKeyboardFocus(eventScreen);
        // menuInputHanlder.setShowMenu(false);
    }

    @Override
    public void onLastMessageRead(){
        // if(logicHandler.phase == CombatPhase.ACTIONSELECT){
        //     combatMenu.checkForCompletion();
        // }
        combatMenu.setReadingMessages(false);
    }

    @Override
    public void onEventScreenFocus(){
        combatMenu.setActive(false);
        uiStage.setKeyboardFocus(eventScreen);
        menuInputHanlder.setHandlerDisabled(true);


    }

    @Override
    public void onLoss(){
        Gdx.app.log("Combat", "Sending signal to game over");
        combatScreenObserver.onCombatLoss();

    }

    @Override
    public void onVictory(){
        Gdx.app.log("Combat", "Sending signal to victory");
        combatScreenObserver.onCombatVictory();

    }


    public void addListener(CombatScreenObserver observer){
        combatScreenObserver = observer;
    }

    @Override
    public void dispose(){
        uiStage.dispose();
        // clean up mf
    }

}
