package dungeon.crawler.GameSystem.DebugConsole;

import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.GameState.GameState;
import dungeon.crawler.Utils.PartyUtils;

public class DebugConsoleCommands {
    private GameState gameState;
    public DebugConsoleCommands(
        GameState gameState
    ){
        this.gameState = gameState;
    }
    // For now. Syntax is method_name; param1, param2
    public void parseCommand(String command){
        String[] args = command.split(";");
        String methodName = args[0];
        switch(methodName){
            case "heal":
                String[] params = args[1].split(",");
                String name = params[0];
                String health = params[1];
                try {
                    int healthAmount = Integer.parseInt(health);
                    heal(name, healthAmount);

                } catch(Exception e) {
                    throw e;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid command");
        }
    }

    public void heal(String name, int amount){
        PartyCharacter character = PartyUtils.returnPartyMemberByName(this.gameState.party, name);
        if(character != null){
            int boost = character.heal(amount);
        }
    }
}
