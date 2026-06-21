package dungeon.crawler.GameSystem.DebugConsole;

import com.vabrant.console.gui.GUIConsole;

import dungeon.crawler.GameSystem.GameState.GameState;

public class DebugConsoleWrapper {
    public GUIConsole console;
    public GameState gameState;

    public DebugConsoleWrapper(
        GameState gameState
    ){
        // If the debug console causes issues with the HTML version of the game, handle it here
        this.gameState = gameState;
        this.console = new GUIConsole();
        this.console.setStrategy(new ConsoleExecutionStrategy(gameState));
    }


}
