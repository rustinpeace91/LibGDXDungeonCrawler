package dungeon.crawler;


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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import dungeon.crawler.GameSystem.Combat.CombatLogic;
import dungeon.crawler.GameSystem.GameState.CombatActionState;
import dungeon.crawler.GameSystem.GameState.CombatPhase;
import dungeon.crawler.Menu.CombatEventScreen;
import dungeon.crawler.Menu.CombatMenu;
import dungeon.crawler.Menu.CombatPartyOrderScreen;
import dungeon.crawler.Menu.CurrentFighterStatusScreen;
import dungeon.crawler.Menu.MenuInputHandler;
import dungeon.crawler.Observers.ActionSelectObserver;
import dungeon.crawler.Observers.CombatLogicObserver;
import dungeon.crawler.Observers.CombatScreenObserver;
import dungeon.crawler.Observers.EventScreenObserver;
import dungeon.crawler.Observers.MenuInputObserver;

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
    
    public CombatScreen(
        MainGame game
    ) {
        this.game = game;
        this.addListener(game);

        // 1. Setup Stage and Table
        this.uiStage = new Stage(new ScreenViewport());

        // 2. Load your Skin (ensure path is correct)
        this.skin = new Skin(Gdx.files.internal(GameConstants.MENU_SKIN));


        this.batch = new SpriteBatch();
        this.backgroundTexture = new Texture(Gdx.files.internal("libgdungeonPOC.png"));
            // 1. Load the PNG
        Texture texture = new Texture(Gdx.files.internal("libgdungeonPOC.png"));

        // 2. Wrap it in an Image actor
        Image imageActor = new Image(texture);



        // 3. Position and add it
        // imageActor.setPosition(100, 100);
        imageActor.setScaling(Scaling.stretch); // This forces it to stretch to the actor's bounds

        // 2. Tell it to fill the entire stage
        imageActor.setFillParent(true); 

        uiStage.addActor(imageActor);


        Texture ratTexture = new Texture(Gdx.files.internal("Sprites/Enemies/testrat2.png"));
        Image testRatImage = new Image(ratTexture );
        testRatImage.setPosition(250, 100);
        testRatImage.setScale(0.45f);
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
            String.format("A %s pops up!", enemyName),
            "prepare to fight"
        };
        eventScreen.addMessages(introText);
        eventScreen.showNextMessage();
        this.uiStage.setKeyboardFocus(eventScreen);
        this.logicHandler = new CombatLogic(eventScreen, game);
        this.logicHandler.addListener(this);
        this.logicHandler.handleState(CombatPhase.INTRO);
    }

    private void setUpMenu() {
        // this.uiStage = new Stage(new ScreenViewport());
        CombatMenu menu = new CombatMenu(this.skin);

        menu.addScreenChangeObserver(game);
        menu.addActionSelectObserver(this);
        // menu.setOrigin(Align.right); 
        // menu.setOrigin(Align.bottom);
        
        // consider making these mwnua  properties
        float x = Gdx.graphics.getWidth() - menu.getWidth();
        float y = 0;
        menu.setPosition(x, y);
        this.uiStage.addActor(menu);
        combatMenu = menu;

        eventScreen = new CombatEventScreen(this.skin);
        
        eventScreen.setPosition(
            (uiStage.getWidth() - eventScreen.getWidth()) / 2f, 
            10f
        );
        this.uiStage.addActor(eventScreen);
        eventScreen.addListener(this);

        partyScreen = new CombatPartyOrderScreen(skin);

        partyScreen.setText(
            String.format("PLYR \n HP: %s \n MP: 0", String.valueOf(this.game.gameState.player.hp))
        );
        partyScreen.setPosition(0, 0);
        this.uiStage.addActor(partyScreen);

        float statusScreenHeight = Math.abs(menu.getHeight() - uiStage.getHeight());
        CurrentFighterStatusScreen currentFighterScreen = new CurrentFighterStatusScreen(skin, statusScreenHeight);
        currentFighterScreen.setText(
            String.format("%s\n-various\n-ailments\n-will\n-go\n-here", this.game.gameState.player.name)
        );
        currentFighterScreen.setPosition(
            (uiStage.getWidth() - currentFighterScreen.getWidth()),
            menu.getHeight()
        );
        this.uiStage.addActor(currentFighterScreen);

        this.menuInputHanlder = new MenuInputHandler(
            uiStage,
            menu
        );
        this.menuInputHanlder.addListener(this);
        this.menuInputHanlder.setShowMenu(false);
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
        if(!eventScreen.messageQueue.isEmpty()){
            partyScreen.setText(
                String.format("PLYR \n HP: %s \n MP: 0", String.valueOf(this.game.gameState.player.hp))
            );
        }
        logicHandler.advanceCombat();
    }

    // @Override
    // public void resize(int width, int height) {
    //     // This updates the Stage's internal coordinate system to match the window
    //     uiStage.getViewport().update(width, height, true);
    // }

    // TODO: untangle this from menuInputHanlder
    @Override
    public void onMenuToggled(boolean value) {}

    @Override
    public void onActionMenuFocus(){
        uiStage.setKeyboardFocus(combatMenu);
        menuInputHanlder.setShowMenu(true);
    }

    @Override
    public void onActionSelect(CombatActionState actionState){
        logicHandler.addAction(1, actionState, 1);
        // CombatAction newAction = new CombatAction(
        //     1
        // )
        // logicHandler.actionQueue.add(e)
    }

    @Override
    public void onActionSelectComplete(){
        menuInputHanlder.setShowMenu(false);
        uiStage.setKeyboardFocus(eventScreen);
    }

    @Override
    public void onFirstMessageAdded(){
        // uiStage.setKeyboardFocus(eventScreen);
        // menuInputHanlder.setShowMenu(false);
    }

    @Override
    public void onLastMessageRead(){
        
    }

    @Override
    public void onLoss(){
        Gdx.app.log("Combat", "Sending signal to game over");
        combatScreenObserver.onCombatLoss();

    }

    @Override
    public void onVictory(){
        Gdx.app.log("Combat", "Sending signal to victory");
        // TODO: bad
        this.game.gameState.player.xp = this.game.gameState.player.xp + this.logicHandler.xpGained;
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
