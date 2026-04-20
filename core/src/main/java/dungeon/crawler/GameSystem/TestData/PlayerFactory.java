package dungeon.crawler.GameSystem.TestData;

import java.util.ArrayList;

import dungeon.crawler.GameSystem.Character.CharacterClass;
import dungeon.crawler.GameSystem.Character.Condition;
import dungeon.crawler.GameSystem.Character.PartyCharacter;
import dungeon.crawler.GameSystem.Character.Stance;

public class PlayerFactory {

    public static PartyCharacter generate() {
        PartyCharacter pc = new PartyCharacter(
            "Hero",
            30,
            10,
            30,
            10,
            0,
            10,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            10,
            10,
            10,
            10,
            CharacterClass.HERO,
            true
        );
        pc.equipWeapon(WeaponFactory.getIronSword());
        return pc;
    }

    public static PartyCharacter generatePartyMember() {
        PartyCharacter pc = new PartyCharacter(
            "Foighter",
            30,
            10,
            30,
            10,
            0,
            10,
            Stance.STANDING,
            new ArrayList<Condition>(),
            false,
            1,
            10,
            10,
            10,
            10,
            CharacterClass.FIGHTER,
            false
        );
        pc.equipWeapon(WeaponFactory.getIronSword());
        return pc;
    }


}