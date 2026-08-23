package dungeon.crawler.GameSystem.TestData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import dungeon.crawler.GameConstants;
import dungeon.crawler.GameSystem.Character.Class.*;
import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Character.Stance;

import static dungeon.crawler.GameConstants.PLAYER_STATS.*;

import dungeon.crawler.GameSystem.Inventory.Item;
import dungeon.crawler.GameSystem.Inventory.Weapon;
import dungeon.crawler.GameSystem.SaveGame.Serialization.PartyCharacterSave;

public class PlayerFactory{

    public static PartyCharacter generateClass(String selector){
        // TODO: for when we implement savegames
        switch(selector){
            case "Hero":
                return generate();
            case "Fighter":
                return generatePartyMember();
            case "Wizard":
                return generateWizard();
            case "Thief":
                return generateThief();
            default:
                throw new IllegalArgumentException("Unknown class type: " + selector);

        }
    }

    public static ClassLogic blankClassFromString(String selector){
        // TODO: for when we implement savegames
        switch(selector){
            case "Hero":
                return new WizardClass();
            case "Fighter":
                return new FighterClass();
            case "Wizard":
                return new WizardClass();
            case "Thief":
                return new ThiefClass();
            default:
                throw new IllegalArgumentException("Unknown class type: " + selector);
        }
    }

    public static PartyCharacter generateFromSaveState(PartyCharacterSave saveState){
        ClassLogic charClass = blankClassFromString(saveState.getCharClass());
        PartyCharacter newChar = new PartyCharacter(
            saveState.getName(),
            saveState.getMaxHp(),
            saveState.getMaxMP(),
            saveState.getHp(),
            saveState.getMaxMP(),
            saveState.getXp(),
            saveState.getStance(),
            saveState.getConditions(),
            saveState.isDead(),
            saveState.getLevel(),
            saveState.getStrength(),
            saveState.getAgility(),
            saveState.getIntelligence(),
            saveState.getPerception(),
            charClass,
            saveState.isHero()
        );
        newChar.generateFist();
        for(Item item: returnItemsFromSave(saveState.getInventory())){
            newChar.addToInventory(item);
        }
        equipItemsFromInventory(newChar, saveState);
        newChar.charClass.fillSpells(saveState.getLevel());
        return newChar;

    }

    public static ArrayList<Item> returnItemsFromSave(ArrayList<String> itemList){
        ArrayList<Item> inventory = new ArrayList<>();
        ItemFactory factory = new ItemFactory();
        for(String i:itemList) {
            try{
                inventory.add(factory.createItemById(i));
            } catch (IllegalArgumentException exc){
                Gdx.app.log("[LOAD GAME]", "Error.  Item " + i + " does not exist");
            }
        }
        return inventory;
    }

    public static void equipItemsFromInventory(PartyCharacter character, PartyCharacterSave saveState){

        for(Item i: character.returnInventory()) {
            /* thought we were going to need a map here and not just an array. oh well*/
            if(saveState.getEquipment().get(i.getId()) != null){
                character.equipment.equipItem(i);
            }
        }
    }

	public static PartyCharacter generate() {

    	HeroClass hc = new HeroClass();
    	// TODO: Get base stats from charClass method
        //
        Map<GameConstants.PLAYER_STATS, Integer> statMap = hc.returnBaseStats();
        ItemFactory items = new ItemFactory();

        PartyCharacter pc = new PartyCharacter(
            "Hero",
            hc.getBaseHP(),
            hc.getBaseMP(),
            hc.getBaseHP(),
            hc.getBaseMP(),
            0,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            statMap.get(STRENGTH),
            statMap.get(AGILITY),
            statMap.get(INTELLIGENCE),
            statMap.get(PERCEPTION),
            hc,
            true
        );
        pc.generateFist();
        Item sword = items.createWeaponFromID("iron_sword");
        pc.addToInventory(sword);
        pc.equip(sword);
        pc.addToInventory(items.createPotionFromID("small_health_potion"));

        pc.charClass.fillSpells(1);
        return pc;
    }


    public static PartyCharacter generatePartyMember() {
        ItemFactory items = new ItemFactory();
        FighterClass fc = new FighterClass();
        Map<GameConstants.PLAYER_STATS, Integer> statMap = fc.returnBaseStats();
        PartyCharacter pc = new PartyCharacter(
            "Fighter",
            45,
            0,
            45,
            10,
            0,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            statMap.get(STRENGTH),
            statMap.get(AGILITY),
            statMap.get(INTELLIGENCE),
            statMap.get(PERCEPTION),
            fc,
            false
        );
        pc.generateFist();
        pc.equip(items.createWeaponFromID("iron_sword"));
        return pc;
    }

    public static PartyCharacter generateWizard() {
        WizardClass fc = new WizardClass();
        Map<GameConstants.PLAYER_STATS, Integer> statMap = fc.returnBaseStats();
        PartyCharacter pc = new PartyCharacter(
            "Wizard",
            15,
            0,
            15,
            20,
            0,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            statMap.get(STRENGTH),
            statMap.get(AGILITY),
            statMap.get(INTELLIGENCE),
            statMap.get(PERCEPTION),
            fc,
            false
        );
        pc.generateFist();

        pc.charClass.fillSpells(1);
        return pc;

    }

    public static PartyCharacter generateThief() {
        ItemFactory items = new ItemFactory();
        ThiefClass tc = new ThiefClass();
        Map<GameConstants.PLAYER_STATS, Integer> statMap = tc.returnBaseStats();
        PartyCharacter pc = new PartyCharacter(
            "Thief",
            15,
            0,
            15,
            0,
            0,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            statMap.get(STRENGTH),
            statMap.get(AGILITY),
            statMap.get(INTELLIGENCE),
            statMap.get(PERCEPTION),
            tc,
            false
        );
        pc.generateFist();

        Weapon bow = items.createWeaponFromID("wooden_bow");
        pc.addToInventory(bow);
        pc.equip(bow);
        return pc;

    }


}
