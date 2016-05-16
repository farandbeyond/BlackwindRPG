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
    Random rand;
    public HealingSpell(BattleEntity caster,String name, String description, int baseHeal, int rollHeal, int cost){
        super(caster,name,description,cost);
        this.baseHeal=baseHeal;
        this.rollHeal=rollHeal;
        rand = new Random();
    }
    public HealingSpell(String name, String description,int baseHeal, int rollHeal, int cost){
        super(null,name,description,cost);
        this.baseHeal=baseHeal;
        this.rollHeal=rollHeal;
        rand = new Random();
    }

    @Override
    public String cast(BattleEntity target) {
        int heal = 0;
        getCaster().useMp(getCost());
        heal+=getCaster().getStat(StatID.INT)/3;
        heal+=baseHeal+rand.nextInt(rollHeal);
        heal-=target.getStat(StatID.RES);
        target.damage(heal);
        return String.format("%s healed %s for %d with %s", getCaster().getName(),target.getName(),heal,getName());
    }
    //gets
    public int getBaseHeal(){return baseHeal;}
    public int getRollHeal(){return rollHeal;}
    public int getMaxHeal(){return baseHeal+rollHeal;}
}
