/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleEntity;
import Background.StatID;
import java.util.Random;

/**
 *
 * @author Connor
 */
public class Weapon extends Equipment{
    private int baseDamage, rollDamage;
    private int secondStatToIncrease, increase;
    Random rand;
    public Weapon(int id, String name, String description, int quantity, int maxQuantity, int baseDamage, int rollDamage, int statToIncrease, int increase, int shopValue){
        super(id,name,description,quantity,maxQuantity,shopValue);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.secondStatToIncrease = statToIncrease;
        this.increase=increase;
        rand = new Random();
    }
    @Override
    public void equip(BattleEntity target) {
        this.setEquipper(target);
        getEquipper().increaseStat(secondStatToIncrease, increase);
        
    }

    @Override
    public void unEquip() {
        getEquipper().reduceStat(secondStatToIncrease, increase);
        setEquipper(null);
    }

    @Override
    public void use(BattleEntity target) {
        System.out.println("No use");
    }
    public int attackDamage(){
        return baseDamage+rand.nextInt(rollDamage)+getEquipper().getStat(StatID.STR)/3;
    }
    public String toString(){
        return String.format("%s\nDeals %d to %d damage\nincreases %s by %d",super.toString(),baseDamage, baseDamage+rollDamage,StatID.getStatName(secondStatToIncrease),increase);
    }
}
