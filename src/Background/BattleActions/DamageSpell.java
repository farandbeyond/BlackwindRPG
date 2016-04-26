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
public class DamageSpell extends Spell{
    private final int baseDamage, rollDamage, element, cost;
    Random rand;
    public DamageSpell(BattleEntity caster, int baseDamage, int rollDamage, int element, int cost){
        super(caster);
        this.cost=cost;
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.element=element;
        rand = new Random();
    }
    public DamageSpell(int baseDamage, int rollDamage, int element, int cost){
        super(null);
        this.cost=cost;
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.element=element;
        rand = new Random();
    }
    @Override
    public void cast(BattleEntity target) {
        int damage = 0;
        getCaster().useMp(cost);
        damage+=getCaster().getStat(StatID.INT)/3;
        damage+=baseDamage+rand.nextInt(rollDamage);
        damage-=target.getStat(StatID.RES);
        damage*=ElementHandler.handler(element, target.getElement());
        target.damage(damage);
    }
    
}
