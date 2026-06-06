package dungeon.crawler.GameSystem.Magic;

import java.util.ArrayList;

public class MagicSystem {
    public ArrayList<Spell> availableSpells;

    public MagicSystem(){
        this.availableSpells = new ArrayList();
    }

    public void addSpell(Spell spell){
        this.availableSpells.add(spell);
    }

    public void setSpells(ArrayList<Spell> spells){
        this.availableSpells = spells;
    }

}
