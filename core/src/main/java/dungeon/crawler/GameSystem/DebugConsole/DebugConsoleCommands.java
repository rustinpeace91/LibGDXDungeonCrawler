package dungeon.crawler.GameSystem.DebugConsole;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Utils.PartyUtils;
import dungeon.crawler.Utils.StringUtils;

public class DebugConsoleCommands {
    private GameState gameState;
    public DebugConsoleCommands(
        GameState gameState
    ){
        this.gameState = gameState;
    }
    // For now. Syntax is method_name param1,param2
    public String parseCommand(String command){
        String[] args = command.split(" ");
        String methodName = args[0];
        switch(methodName){
            case "heal":
                String[] params = args[1].split(",");
                String name = params[0];
                String health = params[1];
                try {
                    int healthAmount = Integer.parseInt(health);
                    String message = heal(name, healthAmount);
                    return message;
                } catch(Exception e) {
                    return e.getMessage();
                }
                // break;
            default:
                return StringUtils.format("Invalid Command %s", command);
        }
    }

    public String heal(String name, int amount){
        PartyCharacter character = PartyUtils.returnPartyMemberByName(this.gameState.party, name);
        String returnLog = "";
        if(character != null){
            int boost = character.heal(amount);
            returnLog = StringUtils.format("%s healed for %s HP", character.name, String.valueOf(boost));
        }
        return returnLog;
    }
}
