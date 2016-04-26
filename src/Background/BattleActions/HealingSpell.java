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
    private final int baseHeal, rollHeal, cost;
    Random rand;
    public HealingSpell(BattleEntity caster, int baseDamage, int rollDamage, int element, int cost){
        super(caster);
        this.cost=cost;
        this.baseHeal=baseDamage;
        this.rollHeal=rollDamage;
        rand = new Random();
    }
    public HealingSpell(int baseDamage, int rollDamage, int element, int cost){
        super(null);
        this.cost=cost;
        this.baseHeal=baseDamage;
        this.rollHeal=rollDamage;
        rand = new Random();
    }

    @Override
    public void cast(BattleEntity target) {
        int heal = 0;
        getCaster().useMp(cost);
        heal+=getCaster().getStat(StatID.INT)/3;
        heal+=baseHeal+rand.nextInt(rollHeal);
        heal-=target.getStat(StatID.RES);
        target.damage(heal);
    }
}
