package dungeon.crawler.GameSystem.DebugConsole;

import com.badlogic.gdx.Gdx;
import com.vabrant.console.executionstrategy.ExecutionStrategy;
import com.vabrant.console.executionstrategy.ExecutionStrategyInput;

import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Utils.StringUtils;


public class ConsoleExecutionStrategy implements ExecutionStrategy{

    DebugConsoleCommands commandHandler;
    GameState gameState;
    public ConsoleExecutionStrategy(GameState gameState){
        commandHandler = new DebugConsoleCommands(gameState);
    }

    @Override
    public Object execute(ExecutionStrategyInput t) throws Exception {
        String input = t.getText();
        try {
            String output = commandHandler.parseCommand(input);
            Gdx.app.log("Console", output);
        } catch(Exception e){
            // no complicated handling, just don't break the damn game for now
            Gdx.app.log("Console", StringUtils.format("ERROR: %s", e.getMessage()));
        }
        return null;
    }
    
}
