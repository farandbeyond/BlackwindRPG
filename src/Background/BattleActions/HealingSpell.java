/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;
import Background.ElementHandler;
import Background.StatID;
import java.util.Random;

/**
 *
 * @author Connor
 */
public class HealingSpell extends Spell{
    private final int baseHeal, rollHeal;
    private final boolean revives;
    Random rand;
    public HealingSpell(BattleEntity caster,String name, String description, int baseHeal, int rollHeal,boolean revives, int cost){
        super(caster,name,description,cost);
        this.baseHeal=baseHeal;
        this.rollHeal=rollHeal;
        this.revives=revives;
        rand = new Random();
    }
    public HealingSpell(String name, String description,int baseHeal, int rollHeal,boolean revives, int cost){
        super(null,name,description,cost);
        this.baseHeal=baseHeal;
        this.rollHeal=rollHeal;
        this.revives=revives;
        rand = new Random();
    }

    @Override
    public String cast(BattleEntity target) {
        int heal = 0;
        rand.setSeed(System.currentTimeMillis());
        getCaster().useMp(getCost());
        heal+=getCaster().getStat(StatID.INT)/3;
        heal+=baseHeal+rand.nextInt(rollHeal);
        if(revives){
            target.raise(heal);
            return String.format("%s raised %s for %d with %s", getCaster().getName(),target.getName(),heal,getName());
        }
        else{
            target.heal(heal);
            return String.format("%s healed %s for %d with %s", getCaster().getName(),target.getName(),heal,getName());
        }
    }
    //gets
    public int getBaseHeal(){return baseHeal;}
    public int getRollHeal(){return rollHeal;}
    public int getMaxHeal(){return baseHeal+rollHeal;}
    public int getElement(){return ElementHandler.NEUTRAL;}
    public boolean targetsAllies(){return true;}
}
