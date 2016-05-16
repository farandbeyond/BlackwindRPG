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
    private final int baseDamage, rollDamage, element;
    Random rand;
    public DamageSpell(BattleEntity caster,String name, String description, int baseDamage, int rollDamage, int element, int cost){
        super(caster,name,description,cost);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.element=element;
        rand = new Random();
    }
    public DamageSpell(String name, String description,int baseDamage, int rollDamage, int element, int cost){
        super(null,name,description,cost);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.element=element;
        rand = new Random();
    }
    @Override
    public String cast(BattleEntity target) {
        int damage = 0;
        getCaster().useMp(getCost());
        damage+=getCaster().getStat(StatID.INT)/3;
        damage+=baseDamage+rand.nextInt(rollDamage);
        damage-=target.getStat(StatID.RES);
        damage*=ElementHandler.handler(element, target.getElement());
        target.damage(damage);
        return String.format("%s dealt %d damage to %s with %s", getCaster().getName(),damage, target.getName(), getName());
    }
    //gets
    public int getBaseDamage(){return baseDamage;}
    public int getRollDamage(){return rollDamage;}
    public int getMaxDamage(){return baseDamage+rollDamage;}
    public int getElement(){return element;}
    
}
