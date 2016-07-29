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
    public DamageSpell(int id,BattleEntity caster,String name, String description, int baseDamage, int rollDamage, int element, int cost){
        super(id,caster,name,description,cost);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.element=element;
        rand = new Random();
    }
    public DamageSpell(int id,String name, String description,int baseDamage, int rollDamage, int element, int cost){
        super(id,null,name,description,cost);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.element=element;
        rand = new Random();
    }
    @Override
    public String cast(BattleEntity target) {
        int damage = 0;
        //System.out.println("Starting Damage:"+damage);
        rand.setSeed(System.currentTimeMillis());
        getCaster().useMp(getCost());
        damage+=getCaster().getStat(StatID.INT)/2;
        //System.out.println("Stat buffed Damage:"+damage);
        damage+=baseDamage+rand.nextInt(rollDamage);
        //System.out.println("Skill buffed Damage:"+damage);
        damage-=target.getStat(StatID.RES);
        //System.out.println("Enemy Resistance Debuffed Damage:"+damage);
        if(ElementHandler.handler(element, target.getElement())<0)
            damage*=ElementHandler.handler(element, target.getElement());
        else{
            damage*=ElementHandler.handler(element, target.getElement());
            if(damage<=0)
                damage = 1;
        }
        //System.out.println("Element Modified Damage:"+damage);
        target.damage(damage);
        return String.format("%s dealt %d damage to %s with %s", getCaster().getName(),damage, target.getName(), getName());
    }
    //gets
    public int getBaseDamage(){return baseDamage;}
    public int getRollDamage(){return rollDamage;}
    public int getMaxDamage(){return baseDamage+rollDamage;}
    public int getElement(){return element;}
    public boolean targetsAllies(){return false;}
    
}
