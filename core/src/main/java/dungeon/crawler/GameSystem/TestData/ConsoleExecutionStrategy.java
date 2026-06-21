package dungeon.crawler.GameSystem.TestData;

import com.badlogic.gdx.Gdx;
import com.vabrant.console.executionstrategy.ExecutionStrategy;
import com.vabrant.console.executionstrategy.ExecutionStrategyInput;

import dungeon.crawler.Utils.StringUtils;


public class ConsoleExecutionStrategy implements ExecutionStrategy{

    @Override
    public Object execute(ExecutionStrategyInput t) throws Exception {
        String input = t.getText();
        String output = StringUtils.format("You TYPED %s into the console", input);
        Gdx.app.log("Console", output);
        Object rValue = new Object();
        return rValue;

    }
    
}
