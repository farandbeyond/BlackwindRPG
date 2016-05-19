/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;
import Background.ElementHandler;
import java.util.Random;

/**
 *
 * @author Connor
 */
public class PhysicalAction extends BattleAction{
    private int baseDamage, rollDamage;
    private Random rand;
    private int element;
    private int damageStat, resistStat;
    
    public PhysicalAction(BattleEntity caster,String name, String description,int baseDamage, int rollDamage, int damageStat, int resistStat, int element){
        super(caster,name,description);
        this.damageStat=damageStat;
        this.resistStat=resistStat;
        this.baseDamage=baseDamage;
        this.rollDamage = rollDamage;
        this.element=element;
        rand = new Random();
    }
    public PhysicalAction(String name, String description,int baseDamage, int rollDamage, int damageStat, int resistStat, int element){
        super(null,name,description);
        this.damageStat=damageStat;
        this.resistStat=resistStat;
        this.baseDamage=baseDamage;
        this.rollDamage = rollDamage;
        this.element=element;
        rand = new Random();
    }
    public String execute(BattleEntity target){
        int damage = 0;
        if(getCaster().getWeapon()!=null){
            damage+=getCaster().getWeapon().attackDamage();
        }
        damage+=baseDamage+rand.nextInt(rollDamage)+getCaster().getStat(damageStat)/6;
        damage-=target.getStat(resistStat)/3;
        if(ElementHandler.handler(element, target.getElement())<0)
            damage*=ElementHandler.handler(element, target.getElement());
        else{
            damage*=ElementHandler.handler(element, target.getElement());
            if(damage<=0)
                damage = 1;
        }
        target.damage(damage);
        return String.format("%s dealt %d damage to %s with %s", getCaster().getName(),damage, target.getName(), getName());
    }
    //gets
    public int getCost(){return 0;}
    public int getBaseDamage(){return baseDamage;}
    public int getRollDamage(){return rollDamage;}
    public int getMaxDamage(){return baseDamage+rollDamage;}
    public int getElement(){return element;}
    public int getDamageStat(){return damageStat;}
    public int getResistStat(){return resistStat;}
    public boolean targetsAllies(){return false;}
}
